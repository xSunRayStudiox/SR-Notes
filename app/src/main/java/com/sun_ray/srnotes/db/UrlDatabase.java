package com.sun_ray.srnotes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sun_ray.srnotes.dao.UrlDao;
import com.sun_ray.srnotes.model.Url;

@Database(entities = Url.class, version = 1, exportSchema = false)
public abstract class UrlDatabase extends RoomDatabase {
    private static UrlDatabase instance;

    public static synchronized UrlDatabase getDB(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context, UrlDatabase.class, "Urls")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return instance;
    }

    public abstract UrlDao urlDao();
}
