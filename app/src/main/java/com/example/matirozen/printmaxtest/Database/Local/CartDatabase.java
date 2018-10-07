package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;

@Database(entities = {Cart.class}, version = 2)
public abstract  class CartDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    public static CartDatabase instance;

    static final Migration MIGRATION_3_2 = new Migration(3, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static CartDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, CartDatabase.class, "PrintmaxTEST")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_3_2)
                    .build();
        }
        return instance;
    }
}
