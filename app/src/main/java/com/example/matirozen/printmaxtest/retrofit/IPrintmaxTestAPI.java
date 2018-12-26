package com.example.matirozen.printmaxtest.Retrofit;

import com.example.matirozen.printmaxtest.Database.ModelDB.Price;
import com.example.matirozen.printmaxtest.Model.Category;
import com.example.matirozen.printmaxtest.Model.Order;
import com.example.matirozen.printmaxtest.Model.Precio;
import com.example.matirozen.printmaxtest.Model.Tag;
import com.example.matirozen.printmaxtest.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IPrintmaxTestAPI {
    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name);

    @FormUrlEncoded
    @POST("gettag.php")
    Call<List<Tag>> getTag(@Field("menuId") String menuId);

    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);


    @GET("getmenu.php")
    Call<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("submitorder.php")
    Call<String> submitOrder(@Field("price") float orderPrice,
                             @Field("comment") String comment,
                             @Field("phone") String phone);

    @FormUrlEncoded
    @POST("submitelement.php")
    Call<String> submitElement(@Field("etiqueta") String etiqueta,
                             @Field("cantidad") float cantidad,
                             @Field("unidad") String unidad,
                             @Field("material") String material,
                             @Field("ancho") int ancho,
                             @Field("largo") float largo,
                             @Field("colores") int colores,
                             @Field("presentacion") String presentacion,
                             @Field("price") double price,
                             @Field("orderId") int orderId);

    @FormUrlEncoded
    @POST("getprice.php")
    Call<Precio> getPrice(@Field("code") String code);

    @GET("getlastorder.php")
    Call<List<Order>> getLastOrder();

    @GET("getallprices.php")
    Call<ArrayList<Precio>> getAllPrices();

}
