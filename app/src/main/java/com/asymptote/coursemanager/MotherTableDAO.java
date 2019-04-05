package com.asymptote.coursemanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MotherTableDAO {

    @Query("SELECT * FROM Courses")
    List<Courses> getAll();

    @Query("SELECT * FROM Courses WHERE course_name like :course_name")
    Courses findByCourse(String course_name);


//    @Query("UPDATE Courses SET books=:text WHERE directory=:dir")
//    void UpdateColumnBydir (String text, String dir);

    @Query("SELECT COUNT(*) FROM Courses")
    int count_courses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Courses... courses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Courses course);

    @Delete
    void delete(Courses courses);

}
