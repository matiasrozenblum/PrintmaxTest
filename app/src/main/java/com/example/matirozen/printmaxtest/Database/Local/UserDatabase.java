package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;
import com.example.matirozen.printmaxtest.Database.ModelDB.Price;
import com.example.matirozen.printmaxtest.Database.ModelDB.TagDB;
import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

@Database(entities = {UserDB.class, Cart.class, Price.class, TagDB.class}, version = 4)
public abstract  class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract CartDAO cartDAO();
    public abstract PriceDAO priceDAO();
    public abstract TagDAO tagDAO();
    public static UserDatabase instance;

    public static UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, UserDatabase.class, "PrintmaxTEST")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_2_3 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Price (codigo TEXT NOT NULL, precioa REAL NOT NULL, preciob REAL NOT NULL, precioc REAL NOT NULL, preciod REAL NOT NULL, precioe REAL NOT NULL, PRIMARY KEY(codigo))");
        }
    };
}
