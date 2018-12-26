package com.example.matirozen.printmaxtest.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.matirozen.printmaxtest.Database.ModelDB.Price;
import com.example.matirozen.printmaxtest.Database.ModelDB.TagDB;

import java.util.List;

@Dao
public interface TagDAO {

    @Query("SELECT * FROM TagDB")
    List<TagDB> getTags();

    @Insert
    void insertIntoTagDB(TagDB...tagDBs);

    @Query("DELETE FROM TagDB")
    void nukeTable();
}
