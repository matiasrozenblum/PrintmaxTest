package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {
    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>> getCartItemById(int cartItemId);
    int countCartItems();
    float sumPrice();
    void emptyCart();
    void insertIntoCart(Cart...Carts);
    void updateCart(Cart...Carts);
    void deleteCartItem(Cart cart);
}
