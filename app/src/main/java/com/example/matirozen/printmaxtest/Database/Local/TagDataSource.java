package com.example.matirozen.printmaxtest.Database.Local;

import com.example.matirozen.printmaxtest.Database.DataSource.IPriceDataSource;
import com.example.matirozen.printmaxtest.Database.DataSource.ITagDataSource;
import com.example.matirozen.printmaxtest.Database.ModelDB.Price;
import com.example.matirozen.printmaxtest.Database.ModelDB.TagDB;


import java.util.List;

import io.reactivex.Flowable;

public class TagDataSource implements ITagDataSource {

    private TagDAO tagDAO;
    private static TagDataSource instance;

    public TagDataSource(TagDAO tagDAO){
        this.tagDAO = tagDAO;
    }

    public static TagDataSource getInstance(TagDAO tagDAO){
        if(instance == null)
            instance = new TagDataSource(tagDAO);
        return instance;
    }

    @Override
    public List<TagDB> getTags(){
        return tagDAO.getTags();
    }

    @Override
    public void insertIntoTagDB(TagDB...tagDBs) {
        tagDAO.insertIntoTagDB(tagDBs);
    }

    @Override
    public void nukeTable(){
        tagDAO.nukeTable();
    }
}
