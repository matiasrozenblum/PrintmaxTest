package com.example.matirozen.printmaxtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.matirozen.printmaxtest.Adapter.CategoryAdapter;
import com.example.matirozen.printmaxtest.Adapter.DrinkAdapter;
import com.example.matirozen.printmaxtest.Database.DataSource.CartRepository;
import com.example.matirozen.printmaxtest.Database.Local.CartDataSource;
import com.example.matirozen.printmaxtest.Database.Local.CartDatabase;
import com.example.matirozen.printmaxtest.Model.Banner;
import com.example.matirozen.printmaxtest.Model.Category;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.Retrofit.IPrintmaxTestAPI;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.facebook.accountkit.AccountKit;
import com.nex3z.notificationbadge.NotificationBadge;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtName, txtPhone;
    RecyclerView lst_drink;
    NotificationBadge badge;
    ImageView cartIcon;

    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lst_drink = (RecyclerView)findViewById(R.id.recycler_drinks);
        lst_drink.setLayoutManager(new GridLayoutManager(this, 2));
        lst_drink.setHasFixedSize(true);
        loadListDrink("6");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtName = (TextView)headerView.findViewById(R.id.txtName);
        txtPhone = (TextView)headerView.findViewById(R.id.txtPhone);

        //Set info
        Log.d("PATITA", PrintmaxTestService.currentUser.toString());
        txtName.setText(PrintmaxTestService.currentUser.getName());
        txtPhone.setText(PrintmaxTestService.currentUser.getPhone());
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
        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        lst_drink.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        View view = menu.findItem(R.id.cartMenu).getActionView();
        badge = (NotificationBadge)view.findViewById(R.id.badge);
        cartIcon = (ImageView)view.findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });
        updateCartCount();
        return true;
    }

    private void updateCartCount() {
        if(badge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(PrintmaxTestService.cartRepository.countCartItems() == 0){
                    badge.setVisibility(View.INVISIBLE);
                } else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(PrintmaxTestService.cartRepository.countCartItems()));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cartMenu) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sign_out) {
            // Create confirm dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit Application");
            builder.setMessage("¿Esta seguro que quiere salir?");

            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AccountKit.logOut();

                    //Clear all activity
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }
}
