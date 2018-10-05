package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

public interface IUserDataSource {
    UserDB getUser();
    void insertIntoUser(UserDB...Users);
    void updateUser(UserDB...Users);
    void deleteUserItem(UserDB user);
}
