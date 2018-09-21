package com.example.matirozen.printmaxtest.Utils;

import com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI;
import com.example.matirozen.printmaxtest.Retrofit.RetrofitClient;

public class Common {
    private static final String BASE_URL = "http://192.168.0.12/printmaxtest/";

    public static IPrintmaxTestAPI getAPI(){
        return RetrofitClient.getClient(BASE_URL).create(IPrintmaxTestAPI.class);
    }
}
