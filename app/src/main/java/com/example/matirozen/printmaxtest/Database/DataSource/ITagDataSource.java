package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.TagDB;

import java.util.List;

public interface ITagDataSource {
    List<TagDB> getTags();
    void insertIntoTagDB(TagDB...tagDBs);
    void nukeTable();
}
