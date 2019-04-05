package com.asymptote.coursemanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UsisTable.class}, version = 1)
public abstract class AppDatabase3 extends RoomDatabase {

    private static AppDatabase3 INSTANCE; //singleton style baby

    public abstract UsisDAO usisDAO();

    public static AppDatabase3 getAppDatabase3(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase3.class, "Usis-Database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
