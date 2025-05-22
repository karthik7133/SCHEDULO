package com.carcar.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = { LoginStruct.class,QuestionPaper.class,Daywiseschedule.class}, version = 4)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;

    public abstract LoginDao loginDao();
    public abstract ScheduleDao scheduleDao();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration() // 🔥 Deletes old data and creates a new database
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}