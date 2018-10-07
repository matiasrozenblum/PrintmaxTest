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
    public UserDB getUserDB() {
        return userDAO.getUserDB();
    }

    @Override
    public void insertIntoUserDB(UserDB...Users) {
        userDAO.insertIntoUserDB(Users);
    }

    @Override
    public void updateUserDB(UserDB...Users) {
        userDAO.updateUserDB(Users);
    }

    @Override
    public void deleteUserItemDB(UserDB user) {
        userDAO.deleteUserItemDB(user);
    }
}
