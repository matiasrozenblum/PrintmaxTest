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

import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.example.matirozen.printmaxtest.model.CheckUserResponse;
import com.example.matirozen.printmaxtest.model.User;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
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

        btnContinue = findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startLoginPage(LoginType.PHONE);
            }
        });

        //Check session
        if(AccountKit.getCurrentAccessToken() != null){
            final android.app.AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
            alertDialog.show();
            alertDialog.setMessage("Please waiting...");
            //Auto login
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {
                    PrintmaxTestService.get().checkIfUserExists(account.getPhoneNumber().toString())
                            .enqueue(new Callback<CheckUserResponse>() {
                                @Override
                                public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                    alertDialog.dismiss();
                                    if (!response.isSuccessful()){
                                        return;
                                    }
                                    CheckUserResponse userResponse = response.body();
                                    if(userResponse.isExists()){
                                        //Fetch information
                                        PrintmaxTestService.get().getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        //If User already exists, just start new Activity
                                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    } else {
                                        showRegisterDialog(account.getPhoneNumber().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                    Log.d("hola", "hola");
                                }
                            });
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.d("ERROR", accountKitError.getErrorType().getMessage());
                }
            });
        }
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
        if(requestCode == REQUEST_CODE){
            if (data == null){
                return;
            }
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if(result.getError() != null){
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            } else if(result.wasCancelled()){
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            } else {
                if(result.getAccessToken() != null){
                    final android.app.AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
                    alertDialog.show();
                    alertDialog.setMessage("Please waiting...");

                    //Get user phone and check exists on server
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            PrintmaxTestService.get().checkIfUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<CheckUserResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            alertDialog.dismiss();
                                            if (!response.isSuccessful()){
                                                return;
                                            }
                                            CheckUserResponse userResponse = response.body();
                                            if(userResponse.isExists()){
                                                //Fetch information
                                                PrintmaxTestService.get().getUserInformation(account.getPhoneNumber().toString())
                                                        .enqueue(new Callback<User>() {
                                                            @Override
                                                            public void onResponse(Call<User> call, Response<User> response) {
                                                                //If User already exists, just start new Activity
                                                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                                                finish();
                                                            }

                                                            @Override
                                                            public void onFailure(Call<User> call, Throwable t) {
                                                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            } else {
                                                showRegisterDialog(account.getPhoneNumber().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                            Log.d("hola", "hola");
                                        }
                                    });
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Log.d("ERROR", accountKitError.getErrorType().getMessage());
                        }
                    });
                }
            }
        }
    }

    private void showRegisterDialog(final String phone){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("REGISTER");

        LayoutInflater inflater = this.getLayoutInflater();
        View registerLayout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText edtName = registerLayout.findViewById(R.id.edt_name);
        final MaterialEditText edtAddress = registerLayout.findViewById(R.id.edt_address);
        final MaterialEditText edtBirth = registerLayout.findViewById(R.id.edt_birth);

        Button btnRegister = registerLayout.findViewById(R.id.btn_register);

        edtBirth.addTextChangedListener(new PatternedTextWatcher("####-##-##"));
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

                String address = (edtAddress.getText() != null)? edtAddress.getText().toString() : null;
                String birth = (edtBirth.getText() != null)? edtBirth.getText().toString() : null;
                String name = (edtName.getText() != null)? edtName.getText().toString() : null;

                if(TextUtils.isEmpty(address)){
                    Toast.makeText(MainActivity.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(birth)){
                    Toast.makeText(MainActivity.this, "Please enter your birth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                PrintmaxTestService.get().registerNewUser(phone, name, address, birth)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            waitingDialog.dismiss();

                            if (response.isSuccessful()){
                                User user = response.body();
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

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.matirozen.printmaxtest",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
