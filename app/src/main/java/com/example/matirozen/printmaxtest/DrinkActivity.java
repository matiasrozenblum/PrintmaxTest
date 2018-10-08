package com.example.matirozen.printmaxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.matirozen.printmaxtest.Adapter.BordadasAdapter;
import com.example.matirozen.printmaxtest.Adapter.EstampadasAdapter;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkActivity extends AppCompatActivity {

    RecyclerView lst_drink;
    TextView txt_banner_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        lst_drink = (RecyclerView)findViewById(R.id.recycler_drinks);
        lst_drink.setLayoutManager(new GridLayoutManager(this, 2));
        lst_drink.setHasFixedSize(true);

        txt_banner_name = (TextView)findViewById(R.id.txt_menu_name);
        loadListDrink("6");
    }

    private void loadListDrink(String menuId) {
        PrintmaxTestService.get().getDrink(menuId)
                .enqueue(new Callback<List<Drink>>() {
                    @Override
                    public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                        displayDrinkList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Drink>> call, Throwable t) {
                        Log.d("hola", "hola");
                    }
                });
    }

    private void displayDrinkList(List<Drink> drinks) {
        List<Drink> bordadas = new ArrayList<>();
        bordadas.set(0, drinks.get(0));
        List<Drink> estampadas = new ArrayList<>();
        estampadas.set(0, drinks.get(1));
        BordadasAdapter bordadasAdapter = new BordadasAdapter(this, bordadas);
        EstampadasAdapter estampadasAdapter = new EstampadasAdapter(this, estampadas);
        lst_drink.setAdapter(bordadasAdapter);
        lst_drink.setAdapter(estampadasAdapter);
    }
}
