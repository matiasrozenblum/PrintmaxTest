package com.example.matirozen.printmaxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.matirozen.printmaxtest.Adapter.DrinkAdapter;
import com.example.matirozen.printmaxtest.Model.Banner;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;

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
        loadListDrink(PrintmaxTestService.currentCategory.id);
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

                    }
                });
    }

    private void displayDrinkList(List<Drink> drinks) {
        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        lst_drink.setAdapter(adapter);
    }
}
