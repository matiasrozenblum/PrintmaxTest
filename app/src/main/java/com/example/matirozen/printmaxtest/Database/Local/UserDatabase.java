package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

@Database(entities = {UserDB.class}, version = 3)
public abstract  class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public static UserDatabase instance;

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, UserDatabase.class, "PrintmaxTEST")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return instance;
    }
}
