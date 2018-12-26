package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.Price;

import java.util.List;

import io.reactivex.Flowable;

public interface IPriceDataSource {
    Price getPriceByCode(String codigo);
    void insertIntoPrice(Price...Prices);
    void nukeTable();
}
