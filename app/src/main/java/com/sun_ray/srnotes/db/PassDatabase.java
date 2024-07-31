package com.sun_ray.srnotes.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sun_ray.srnotes.dao.PassDao;
import com.sun_ray.srnotes.model.Password;

@Database(entities = Password.class, version = 1, exportSchema = false)
public abstract class PassDatabase extends RoomDatabase {

    private static PassDatabase instance;

    public static synchronized PassDatabase getDB(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context, PassDatabase.class, "Passwords")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract PassDao passDao();
}
