package com.example.matirozen.printmaxtest.Database.ModelDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Price")
public class Price {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "codigo")
    public String codigo;

    @ColumnInfo(name = "precioa")
    public float precioa;

    @ColumnInfo(name = "preciob")
    public float preciob;

    @ColumnInfo(name = "precioc")
    public float precioc;

    @ColumnInfo(name = "preciod")
    public float preciod;

    @ColumnInfo(name = "precioe")
    public float precioe;
}
