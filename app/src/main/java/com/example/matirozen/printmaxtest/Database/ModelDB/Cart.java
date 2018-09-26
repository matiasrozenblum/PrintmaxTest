package com.example.matirozen.printmaxtest.Database.ModelDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Cart")
public class Cart {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "metros")
    public int metros;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "material")
    public int material;

    @ColumnInfo(name = "formato")
    public int formato;
}
