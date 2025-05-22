package com.carcar.myapplication;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionPaperDao {

    @Insert
    void insert (QuestionPaper questionPaper);


    @Query("SELECT * FROM question_papers WHERE year = :year ORDER BY date DESC")
    List<QuestionPaper> getPapersByYear(String year);
}
