package com.example.matirozen.printmaxtest;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.matirozen.printmaxtest.Adapter.CartAdapter;
import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.example.matirozen.printmaxtest.Utils.RecyclerItemTouchHelper;
import com.example.matirozen.printmaxtest.Utils.RecyclerItemTouchHelperListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    RecyclerView recyclerCart;
    Button btnPlaceOrder;
    CompositeDisposable compositeDisposable;
    List<Cart> cartList =  new ArrayList<>();
    CartAdapter cartAdapter;
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();

        recyclerCart = (RecyclerView)findViewById(R.id.recyclerCart);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerCart);
        btnPlaceOrder = (Button)findViewById(R.id.btnPlaceOrder);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        loadCartItems();
    }

    private void loadCartItems() {
        compositeDisposable.add(
                PrintmaxTestService.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItems(carts);
                    }
                })
        );
    }

    private void displayCartItems(List<Cart> carts) {
        cartList = carts;
        cartAdapter = new CartAdapter(this, carts);
        recyclerCart.setAdapter(cartAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CartAdapter.CartViewHolder){
            String name = cartList.get(viewHolder.getAdapterPosition()).name;

            final Cart deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            //Delete item from adapter
            cartAdapter.removeItem(deletedIndex);
            //Delete item from Room database
            PrintmaxTestService.cartRepository.deleteCartItem(deletedItem);

            Snackbar snackbar = Snackbar.make(rootLayout, new StringBuilder(name).append(" removed from cart list").toString(), Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartAdapter.restoreItem(deletedItem, deletedIndex);
                    PrintmaxTestService.cartRepository.insertIntoCart(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
