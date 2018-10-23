package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;

@Database(entities = {Cart.class}, version = 4)
public abstract  class CartDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    public static CartDatabase instance;

    static final Migration MIGRATION_2_3 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Cart (id INTEGER NOT NULL, etiqueta TEXT, link TEXT, cantidad INTEGER NOT NULL, unidad TEXT, material INTEGER NOT NULL, ancho INTEGER NOT NULL, largo INTEGER NOT NULL, colores INTEGER NOT NULL, presentacion INTEGER NOT NULL, price FLOAT NOT NULL, PRIMARY KEY(id))");
        }
    };

    public static CartDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, CartDatabase.class, "PrintmaxTEST")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return instance;
    }
}
