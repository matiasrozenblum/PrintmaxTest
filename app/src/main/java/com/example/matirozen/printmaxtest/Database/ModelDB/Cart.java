package com.example.matirozen.printmaxtest.Database.ModelDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "Cart")
public class Cart {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @Nullable
    @ColumnInfo(name = "etiqueta")
    public String etiqueta;


    @Nullable
    @ColumnInfo(name = "link")
    public String link;

    @Nullable
    @ColumnInfo(name = "cantidad")
    public float cantidad;

    @Nullable
    @ColumnInfo(name = "unidad")
    public String unidad;

    @ColumnInfo(name = "material")
    public int material;

    @Nullable
    @ColumnInfo(name = "ancho")
    public int ancho;

    @Nullable
    @ColumnInfo(name = "largo")
    public float largo;

    @Nullable
    @ColumnInfo(name = "colores")
    public int colores;

    @Nullable
    @ColumnInfo(name = "presentacion")
    public int presentacion;

    @Nullable
    @ColumnInfo(name = "price")
    public float price;

}
