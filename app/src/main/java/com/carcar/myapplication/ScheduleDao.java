package com.carcar.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert
    void insert(Daywiseschedule daywiseschedule);

    // Delete a schedule by day and course code
    @Query("DELETE FROM Daywiseschedule WHERE day = :day AND corsecode = :corsecode")
    void deleteByDayAndCourseCode(String day, String corsecode);

    // Get a list of classes for a specific day
    @Query("SELECT * FROM Daywiseschedule WHERE day = :day")
    List<Daywiseschedule> getClassesForDay(String day);


    @Query("SELECT * FROM daywiseschedule")
    List<Daywiseschedule>getall();

}
