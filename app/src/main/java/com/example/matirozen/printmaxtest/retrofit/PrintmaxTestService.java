package com.example.matirozen.printmaxtest.Retrofit;

import com.example.matirozen.printmaxtest.BuildConfig;
import com.example.matirozen.printmaxtest.Database.DataSource.CartRepository;
import com.example.matirozen.printmaxtest.Database.DataSource.UserRepository;
import com.example.matirozen.printmaxtest.Database.Local.CartDatabase;
import com.example.matirozen.printmaxtest.Database.Local.UserDatabase;
import com.example.matirozen.printmaxtest.Model.Banner;
import com.example.matirozen.printmaxtest.Model.Category;
import com.example.matirozen.printmaxtest.Model.CheckUserResponse;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.Model.Order;
import com.example.matirozen.printmaxtest.Model.Price;
import com.example.matirozen.printmaxtest.Model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PrintmaxTestService {

    private static PrintmaxTestService INSTANCE = null;

    private com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI api;
    public static User currentUser = null;
    public static Category currentCategory = null;

    public static int cantidad = -1;
    public static String unidad = "";
    public static int material = -1; //-1: no choice, 0: fasco, 1: saten, 2: poliamida, 3: saten negro
    public static int presentacion = -1; //'1: no choice, 0: cortadas, 1: en rollo
    public static int ancho = -1;
    public static int largo = -1;
    public static int colores = -1;
    public static double price = 0;

    //Database
    public static UserDatabase userDatabase;
    public static UserRepository userRepository;
    public static CartDatabase cartDatabase;
    public static CartRepository cartRepository;

    public static PrintmaxTestService get() {
        if (INSTANCE == null) {
            INSTANCE = new PrintmaxTestService();
        }
        return INSTANCE;
    }

    private PrintmaxTestService(){
        api = RetrofitClient.createClient(BuildConfig.BASE_URL).create(com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI.class);
    }

    public Call<CheckUserResponse> checkIfUserExists(String phone){
        return api.checkExistsUser(phone);
    }

    public Call<User> registerNewUser(String phone, String name){
        return api.registerNewUser(phone, name);
    }

    public Call<User> getUserInformation(String phone){
        return api.getUserInformation(phone);
    }

    public Call<List<Banner>> getBanners(){
        return api.getBanners();
    }

    public Call<List<Category>> getMenu(){
        return api.getMenu();
    }

    public Call<List<Drink>> getDrink(String menuId){
        return api.getDrink(menuId);
    }

    public Call<String> submitOrder(float price, String detail, String comment, String phone){
        return api.submitOrder(price, detail, comment, phone);
    }

    public Call<String> submitElement(String etiqueta, int cantidad, String unidad, String material, int ancho, int largo, int colores, String presentacion, double price, int orderId){
        return api.submitElement(etiqueta, cantidad, unidad, material, ancho, largo, colores, presentacion, price, orderId);
    }

    public Call<Price> getPrice(String code){
        return api.getPrice(code);
    }

    public Call<List<Order>> getLastOrder(){
        return api.getLastOrder();
    }
}
