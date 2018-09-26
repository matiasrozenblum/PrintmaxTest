package com.example.matirozen.printmaxtest.Database.Local;

import com.example.matirozen.printmaxtest.Database.DataSource.ICartDataSource;
import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartDataSource implements ICartDataSource {

    private CartDAO cartDAO;
    private static CartDataSource instance;

    public CartDataSource(CartDAO cartDAO){
        this.cartDAO = cartDAO;
    }

    public static CartDataSource getInstance(CartDAO cartDAO){
        if(instance == null)
            instance = new CartDataSource(cartDAO);
        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCartItems() {
        return cartDAO.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int cartItemId) {
        return cartDAO.getCartItemById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return cartDAO.countCartItems();
    }

    @Override
    public void emptyCart() {
        cartDAO.emptyCart();
    }

    @Override
    public void insertIntoCart(Cart...Carts) {
        cartDAO.insertIntoCart(Carts);
    }

    @Override
    public void updateCart(Cart...Carts) {
        cartDAO.updateCart(Carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        cartDAO.deleteCartItem(cart);
    }
}
