package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.TagDB;

import java.util.List;

public class TagRepository implements ITagDataSource{
    private ITagDataSource iTagDataSource;

    public TagRepository(ITagDataSource iTagDataSource) {
        this.iTagDataSource = iTagDataSource;
    }

    private static TagRepository instance;

    public static TagRepository getInstance(ITagDataSource iTagDataSource){
        if(instance == null)
            instance = new TagRepository(iTagDataSource);
        return instance;
    }

    @Override
    public List<TagDB> getTags(){
        return iTagDataSource.getTags();
    }

    @Override
    public void insertIntoTagDB(TagDB...tagDBs) {
        iTagDataSource.insertIntoTagDB(tagDBs);
    }

    @Override
    public void nukeTable(){
        iTagDataSource.nukeTable();
    }
}
