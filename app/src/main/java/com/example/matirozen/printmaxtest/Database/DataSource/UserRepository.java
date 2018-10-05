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
    public UserDB getUser() {
        return iUserDataSource.getUser();
    }

    @Override
    public void insertIntoUser(UserDB... Users) {
        iUserDataSource.insertIntoUser(Users);
    }

    @Override
    public void updateUser(UserDB... Users) {
        iUserDataSource.updateUser(Users);
    }

    @Override
    public void deleteUserItem(UserDB user) {
        iUserDataSource.deleteUserItem(user);
    }
}
