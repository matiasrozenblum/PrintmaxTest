package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
    Flowable<List<Cart>> getCartItems();

    @Query("SELECT * FROM Cart WHERE id =:cartItemId")
    Flowable<List<Cart>> getCartItemById(int cartItemId);

    @Query("SELECT COUNT(*) FROM Cart")
    int countCartItems();

    @Query("SELECT SUM(price) FROM Cart")
    float sumPrice();

    @Query("DELETE FROM Cart")
    void emptyCart();

    @Insert
    void insertIntoCart(Cart...Carts);

    @Update
    void updateCart(Cart...Carts);

    @Delete
    void deleteCartItem(Cart cart);
}
