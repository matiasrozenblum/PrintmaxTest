package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

public class UserRepository implements IUserDataSource {
    private IUserDataSource iUserDataSource;

    public UserRepository(IUserDataSource iUserDataSource) {
        this.iUserDataSource = iUserDataSource;
    }

    private static UserRepository instance;

    public static UserRepository getInstance(IUserDataSource iUserDataSource){
        if(instance == null)
            instance = new UserRepository(iUserDataSource);
        return instance;
    }

    @Override
    public UserDB getUserDB() {
        return iUserDataSource.getUserDB();
    }

    @Override
    public void insertIntoUserDB(UserDB... Users) {
        iUserDataSource.insertIntoUserDB(Users);
    }

    @Override
    public void updateUserDB(UserDB... Users) {
        iUserDataSource.updateUserDB(Users);
    }

    @Override
    public void deleteUserItemDB(UserDB user) {
        iUserDataSource.deleteUserItemDB(user);
    }
}
