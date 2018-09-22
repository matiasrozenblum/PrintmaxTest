package com.example.matirozen.printmaxtest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.matirozen.printmaxtest.Model.CheckUserResponse;
import com.example.matirozen.printmaxtest.Model.User;
import com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI;
import com.example.matirozen.printmaxtest.Utils.Common;
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

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;
    Button btn_continue;

    IPrintmaxTestAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Common.getAPI();

        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startLoginPage(LoginType.PHONE);
            }
        });
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
                            mService.checkExistsUser(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<CheckUserResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            alertDialog.dismiss();
                                            if (!response.isSuccessful()){
                                                return;
                                            }
                                            CheckUserResponse userResponse = response.body();
                                            if(userResponse.isExists()){
                                                //If User already exists, just start new Activity
                                            } else {
                                                showRegisterDialog(account.getPhoneNumber().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                            Button a;
                                            //Best log ever
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
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("REGISTER");

        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText edt_name = register_layout.findViewById(R.id.edt_name);
        final MaterialEditText edt_address = register_layout.findViewById(R.id.edt_address);
        final MaterialEditText edt_birth = register_layout.findViewById(R.id.edt_birth);

        Button btn_register = register_layout.findViewById(R.id.btn_register);

        edt_birth.addTextChangedListener(new PatternedTextWatcher("####-##-##"));

        //Event
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Close dialog
                alertDialog.create().dismiss();
                final android.app.AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                waitingDialog.setMessage("Please waiting...");

                String address = (edt_address.getText() != null)? edt_address.getText().toString() : null;
                String birth = (edt_birth.getText() != null)? edt_birth.getText().toString() : null;
                String name = (edt_name.getText() != null)? edt_name.getText().toString() : null;

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

                mService.registerNewUser(phone, name, address, birth)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            waitingDialog.dismiss();

                            if (response.isSuccessful()){
                                User user = response.body();
                                if(user != null && TextUtils.isEmpty(user.getError_msg())){
                                    showSuccessToast();
                                    //Start new activity

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

        alertDialog.setView(register_layout);
        alertDialog.show();

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
