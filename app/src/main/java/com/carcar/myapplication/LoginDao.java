package com.carcar.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao

public interface LoginDao {
    @Query("SELECT * FROM LoginStruct")
    List<LoginStruct> getAll();


    @Query("SELECT COUNT(*) FROM LoginStruct WHERE user_name=:username")
    int checkusername(String username);

    @Query("SELECT COUNT(*) FROM LoginStruct WHERE user_name=:name AND Password=:pass")
    int validateuser(String name,String pass);

    @Insert
    void insert(LoginStruct loginStruct);

    @Query("DELETE FROM LoginStruct")
    void deleteall();

    @Query("DELETE FROM LoginStruct WHERE user_name=:user ")
    void deleteuser(String user);
}
