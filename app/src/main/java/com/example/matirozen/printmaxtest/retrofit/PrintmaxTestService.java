package com.example.matirozen.printmaxtest.Retrofit;

import com.example.matirozen.printmaxtest.BuildConfig;
import com.example.matirozen.printmaxtest.model.CheckUserResponse;
import com.example.matirozen.printmaxtest.model.User;

import retrofit2.Call;

public class PrintmaxTestService {

    private static PrintmaxTestService INSTANCE = null;

    private com.example.matirozen.printmaxtest.retrofit.IPrintmaxTestAPI api;
    public static User currentUser = null;

    public static PrintmaxTestService get() {
        if (INSTANCE == null) {
            INSTANCE = new PrintmaxTestService();
        }
        return INSTANCE;
    }

    private PrintmaxTestService(){
        api = RetrofitClient.createClient(BuildConfig.BASE_URL).create(com.example.matirozen.printmaxtest.retrofit.IPrintmaxTestAPI.class);
    }

    public Call<CheckUserResponse> checkIfUserExists(String phone){
        return api.checkExistsUser(phone);
    }

    public Call<User> registerNewUser(String phone, String name, String address, String birth){
        return api.registerNewUser(phone, name, address, birth);
    }

    public Call<User> getUserInformation(String phone){
        return api.getUserInformation(phone);
    }
}
