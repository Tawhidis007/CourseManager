package com.asymptote.coursemanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UsisDAO {

    @Query("SELECT * FROM UsisTable")
    List<UsisTable> getAll();

    @Query("SELECT * FROM UsisTable WHERE course_code like :course_code")
    UsisTable findByCourseCode(String course_code);

    @Query("SELECT COUNT(*) FROM UsisTable")
    int count_courses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UsisTable... usisTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UsisTable usisTable);

    @Delete
    void delete(UsisTable usisTable);






}
