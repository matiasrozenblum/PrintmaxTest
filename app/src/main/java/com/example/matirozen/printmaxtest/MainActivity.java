package com.example.matirozen.printmaxtest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.matirozen.printmaxtest.Database.DataSource.CartRepository;
import com.example.matirozen.printmaxtest.Database.DataSource.UserRepository;
import com.example.matirozen.printmaxtest.Database.Local.CartDataSource;
import com.example.matirozen.printmaxtest.Database.Local.CartDatabase;
import com.example.matirozen.printmaxtest.Database.Local.UserDataSource;
import com.example.matirozen.printmaxtest.Database.Local.UserDatabase;
import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;
import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.example.matirozen.printmaxtest.Model.CheckUserResponse;
import com.example.matirozen.printmaxtest.Model.User;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;

    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();
        if(PrintmaxTestService.userRepository.getUserDB() != null){
            PrintmaxTestService.get().getUserInformation(PrintmaxTestService.userRepository.getUserDB().phone)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //If User already exists, just start new Activity
                            PrintmaxTestService.currentUser = response.body();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            showRegisterDialog();
        }
    }

    private void initDB() {
        PrintmaxTestService.userDatabase = UserDatabase.getInstance(this);
        PrintmaxTestService.userRepository = UserRepository.getInstance(UserDataSource.getInstance(PrintmaxTestService.userDatabase.userDAO()));
        PrintmaxTestService.cartDatabase = CartDatabase.getInstance(this);
        PrintmaxTestService.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(PrintmaxTestService.cartDatabase.cartDAO()));
    }

    private void startLoginPage(LoginType loginType) {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder = new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType, AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, builder.build());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showRegisterDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Registro");

        LayoutInflater inflater = this.getLayoutInflater();
        View registerLayout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText edtName = registerLayout.findViewById(R.id.edt_name);
        final MaterialEditText edtPhone = registerLayout.findViewById(R.id.edt_phone);

        Button btnRegister = registerLayout.findViewById(R.id.btn_register);

        builder.setView(registerLayout);
        final AlertDialog dialog = builder.create();

        //Event
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Close dialog
                dialog.dismiss();
                final android.app.AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                waitingDialog.setMessage("Please waiting...");

                String name = (edtName.getText() != null)? edtName.getText().toString() : null;
                final String phone = (edtPhone.getText() != null)? edtPhone.getText().toString() : null;

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(MainActivity.this, "Ingrese su telefono", Toast.LENGTH_SHORT).show();
                    return;
                }

                PrintmaxTestService.get().registerNewUser(phone, name)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            waitingDialog.dismiss();

                            if (response.isSuccessful()){
                                User user = response.body();
                                try {

                                    //Add to SQLite
                                    UserDB userDB = new UserDB();
                                    final String userPhone = phone;
                                    userDB.phone = userPhone;

                                    //Add to DB
                                    PrintmaxTestService.userRepository.insertIntoUserDB(userDB);
                                    PrintmaxTestService.currentUser = response.body();
                                    Log.d("MATIROZEN_DEBUG", new Gson().toJson(userDB));

                                } catch (Exception ex){
                                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("MATIROZEN_DEBUG", ex.getMessage());
                                }
                                //Aca en vez de preguntar por el error_msg del user preguntas por el isSuccessful
                                //y si no intentas de mapearlo a otro objeto usando el boject mapper
                                //Ej:  RetrofitClient.obtainMapper().readValue(response.errorBody().byteStream(), Error.class);
                                if(user != null){
                                    showSuccessToast();
                                    PrintmaxTestService.currentUser = response.body();
                                    //Start new activity
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            waitingDialog.dismiss();
                        }
                    });
            }

        });

        //alertDialog.setView(registerLayout);
        dialog.show();
    }

    //Fijate que siempre que muestres un toast o llames a un activity estes en un contexto de vista
    //Si lo llamas en un callback estas en otro contexto, ojo con esas cosas porque pueden romper
    private void showSuccessToast(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
