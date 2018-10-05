package com.example.matirozen.printmaxtest.Database.Local;

import com.example.matirozen.printmaxtest.Database.DataSource.IUserDataSource;
import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;


public class UserDataSource implements IUserDataSource {

    private UserDAO userDAO;
    private static UserDataSource instance;

    public UserDataSource(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDAO userDAO){
        if(instance == null)
            instance = new UserDataSource(userDAO);
        return instance;
    }

    @Override
    public UserDB getUser() {
        return userDAO.getUser();
    }

    @Override
    public void insertIntoUser(UserDB...Users) {
        userDAO.insertIntoUser(Users);
    }

    @Override
    public void updateUser(UserDB...Users) {
        userDAO.updateUser(Users);
    }

    @Override
    public void deleteUserItem(UserDB user) {
        userDAO.deleteUserItem(user);
    }
}
