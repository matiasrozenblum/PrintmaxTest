package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.matirozen.printmaxtest.Database.ModelDB.Price;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PriceDAO {
    @Query("SELECT * FROM Price WHERE codigo =:codigo")
    Price getPriceByCode(String codigo);

    @Insert
    void insertIntoPrice(Price...Prices);

    @Query("DELETE FROM Price")
    void nukeTable();
}
