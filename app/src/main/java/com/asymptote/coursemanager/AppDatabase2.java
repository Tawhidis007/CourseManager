package com.asymptote.coursemanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Users.class}, version = 1)
public abstract class AppDatabase2 extends RoomDatabase {

    private static AppDatabase2 INSTANCE; //singleton style baby

    public abstract UserTableDAO userTableDAO();

    public static AppDatabase2 getAppDatabase2(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase2.class, "Users-Database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
