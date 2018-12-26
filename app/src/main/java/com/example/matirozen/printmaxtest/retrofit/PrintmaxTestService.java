package com.example.matirozen.printmaxtest.Retrofit;

import com.example.matirozen.printmaxtest.BuildConfig;
import com.example.matirozen.printmaxtest.Database.DataSource.CartRepository;
import com.example.matirozen.printmaxtest.Database.DataSource.PriceRepository;
import com.example.matirozen.printmaxtest.Database.DataSource.TagRepository;
import com.example.matirozen.printmaxtest.Database.DataSource.UserRepository;
import com.example.matirozen.printmaxtest.Database.Local.UserDatabase;
import com.example.matirozen.printmaxtest.Model.Category;
import com.example.matirozen.printmaxtest.Model.Order;
import com.example.matirozen.printmaxtest.Model.Precio;
import com.example.matirozen.printmaxtest.Model.Tag;
import com.example.matirozen.printmaxtest.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PrintmaxTestService {

    private static PrintmaxTestService INSTANCE = null;

    private com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI api;
    public static User currentUser = null;
    public static Category currentCategory = null;

    public static float cantidad = -1;
    public static String unidad = "";
    public static int material = -1; //-1: no choice, 0: fasco, 1: saten, 2: poliamida, 3: saten negro
    public static int presentacion = -1; //'1: no choice, 0: cortadas, 1: en rollo
    public static int ancho = -1;
    public static float largo = -1;
    public static int colores = -1;
    public static float priceUnidad = 0;
    public static float priceMetro = 0;
    public static float price = 0;

    //Database
    public static UserDatabase userDatabase;
    public static UserRepository userRepository;
    public static CartRepository cartRepository;
    public static PriceRepository priceRepository;
    public static TagRepository tagRepository;

    public static PrintmaxTestService get() {
        if (INSTANCE == null) {
            INSTANCE = new PrintmaxTestService();
        }
        return INSTANCE;
    }

    private PrintmaxTestService(){
        api = RetrofitClient.createClient(BuildConfig.BASE_URL).create(com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI.class);
    }

    public Call<User> registerNewUser(String phone, String name){
        return api.registerNewUser(phone, name);
    }

    public Call<User> getUserInformation(String phone){
        return api.getUserInformation(phone);
    }

    public Call<List<Tag>> getTag(String menuId){
        return api.getTag(menuId);
    }

    public Call<String> submitOrder(float price, String comment, String phone){
        return api.submitOrder(price, comment, phone);
    }

    public Call<String> submitElement(String etiqueta, float cantidad, String unidad, String material, int ancho, float largo, int colores, String presentacion, double price, int orderId){
        return api.submitElement(etiqueta, cantidad, unidad, material, ancho, largo, colores, presentacion, price, orderId);
    }

    public Call<Precio> getPrice(String code){
        return api.getPrice(code);
    }

    public Call<List<Order>> getLastOrder(){
        return api.getLastOrder();
    }

    public Call<ArrayList<Precio>> getAllPrices() {
        return api.getAllPrices();
    }
}
