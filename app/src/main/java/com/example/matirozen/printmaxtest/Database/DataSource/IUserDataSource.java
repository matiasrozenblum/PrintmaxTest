package com.example.matirozen.printmaxtest.Database.DataSource;

import com.example.matirozen.printmaxtest.Database.ModelDB.UserDB;

public interface IUserDataSource {
    UserDB getUserDB();
    void insertIntoUserDB(UserDB...Users);
    void updateUserDB(UserDB...Users);
    void deleteUserItemDB(UserDB user);
}
