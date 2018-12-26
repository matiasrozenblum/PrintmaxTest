package com.example.matirozen.printmaxtest.Database.DataSource;

import android.util.Log;


import com.example.matirozen.printmaxtest.Database.ModelDB.Price;

import java.util.List;

import io.reactivex.Flowable;

public class PriceRepository implements IPriceDataSource {
    private IPriceDataSource iPriceDataSource;

    public PriceRepository(IPriceDataSource iPriceDataSource) {
        this.iPriceDataSource = iPriceDataSource;
    }

    private static PriceRepository instance;

    public static PriceRepository getInstance(IPriceDataSource iPriceDataSource){
        if(instance == null)
            instance = new PriceRepository(iPriceDataSource);
        return instance;
    }

    @Override
    public Price getPriceByCode(String codigo) {
        return iPriceDataSource.getPriceByCode(codigo);
    }

    @Override
    public void insertIntoPrice(Price... Prices){
        iPriceDataSource.insertIntoPrice(Prices);
    }

    @Override
    public void nukeTable(){
        iPriceDataSource.nukeTable();
    }
}
