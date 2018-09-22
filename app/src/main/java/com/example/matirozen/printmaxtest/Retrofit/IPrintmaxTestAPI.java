package com.example.matirozen.printmaxtest.Retrofit;

import com.example.matirozen.printmaxtest.Model.CheckUserResponse;
import com.example.matirozen.printmaxtest.Model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IPrintmaxTestAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkExistsUser(@Field("phone") String phone);

    @POST("register.php")
    Call<User> registerNewUser(@Query("phone") String phone,
                               @Query("name") String name,
                               @Query("address") String address,
                               @Query("birth") String birth);
}
