package com.asymptote.coursemanager;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;

@Dao
public interface UserTableDAO {

    @Query("SELECT * FROM Users")
    List<Users> getAll();

    @Query("SELECT * FROM Users WHERE email like :email")
    Users findByEmail(String email);

    @Query("SELECT COUNT(*) FROM Users")
    int count_users();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Users... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Users users);

    @Delete
    void delete(Users users);

    @RawQuery
    Users getUserQuery(SimpleSQLiteQuery query);

    @RawQuery
    List<Users> getAllUsersQuery(SimpleSQLiteQuery query);

    @RawQuery
    int deleteQuery(SimpleSQLiteQuery query);

    @RawQuery
    int insertQuery(SimpleSQLiteQuery query);

}
