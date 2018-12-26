package com.example.matirozen.printmaxtest.Database.Local;

import com.example.matirozen.printmaxtest.Database.DataSource.IPriceDataSource;
import com.example.matirozen.printmaxtest.Database.ModelDB.Price;


import java.util.List;

import io.reactivex.Flowable;

public class PriceDataSource implements IPriceDataSource {

    private PriceDAO priceDAO;
    private static PriceDataSource instance;

    public PriceDataSource(PriceDAO priceDAO){
        this.priceDAO = priceDAO;
    }

    public static PriceDataSource getInstance(PriceDAO priceDAO){
        if(instance == null)
            instance = new PriceDataSource(priceDAO);
        return instance;
    }

    @Override
    public Price getPriceByCode(String codigo) {
        return priceDAO.getPriceByCode(codigo);
    }

    @Override
    public void insertIntoPrice(Price...Prices) {
        priceDAO.insertIntoPrice(Prices);
    }

    @Override
    public void nukeTable(){
        priceDAO.nukeTable();
    }
}
