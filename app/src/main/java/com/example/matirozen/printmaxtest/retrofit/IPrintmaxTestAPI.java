package com.example.matirozen.printmaxtest.Retrofit;

import com.example.matirozen.printmaxtest.Model.Banner;
import com.example.matirozen.printmaxtest.Model.Category;
import com.example.matirozen.printmaxtest.Model.CheckUserResponse;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IPrintmaxTestAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkExistsUser(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("address") String address,
                               @Field("birth") String birth);

    @FormUrlEncoded
    @POST("getdrink.php")
    Call<List<Drink>> getDrink(@Field("menuId") String menuId);

    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);

    @GET("getbanner.php")
    Call<List<Banner>> getBanners();

    @GET("getmenu.php")
    Call<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("submitorder.php")
    Call<String> submitOrder(@Field("price") float orderPrice,
                             @Field("orderDetail") String orderDetail,
                             @Field("comment") String comment,
                             @Field("address") String address,
                             @Field("phone") String phone);
}
