package com.example.matirozen.printmaxtest.retrofit;

import com.example.matirozen.printmaxtest.BuildConfig;
import com.example.matirozen.printmaxtest.model.CheckUserResponse;
import com.example.matirozen.printmaxtest.model.User;

import retrofit2.Call;

public class PrintmaxTestService {

    private static PrintmaxTestService INSTANCE = null;

    private IPrintmaxTestAPI api;

    public static PrintmaxTestService get() {
        if (INSTANCE == null) {
            INSTANCE = new PrintmaxTestService();
        }
        return INSTANCE;
    }

    private PrintmaxTestService(){
        api = RetrofitClient.createClient(BuildConfig.BASE_URL).create(IPrintmaxTestAPI.class);
    }

    public Call<CheckUserResponse> checkIfUserExists(String phone){
        return api.checkExistsUser(phone);
    }

    public Call<User> registerNewUser(String phone, String name, String address, String birth){
        return api.registerNewUser(phone, name, address, birth);
    }
}
