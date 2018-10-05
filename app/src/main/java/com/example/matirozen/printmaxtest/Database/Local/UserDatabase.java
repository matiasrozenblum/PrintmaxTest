package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

@Database(entities = {UserDB.class}, version = 1)
public abstract  class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public static UserDatabase instance;

    public static UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, UserDatabase.class, "PrintmaxTEST")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
