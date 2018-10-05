package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM UserDB")
    UserDB getUser();

    @Insert
    void insertIntoUser(UserDB...Users);

    @Update
    void updateUser(UserDB...Users);

    @Delete
    void deleteUserItem(UserDB user);
}
