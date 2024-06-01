package com.sun_ray.srnotes.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sun_ray.srnotes.dao.NoteDao;
import com.sun_ray.srnotes.model.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase noteDatabase;

    public static synchronized NoteDatabase getDB(Context context){
        if (noteDatabase == null){
            noteDatabase = Room.databaseBuilder(context, NoteDatabase.class,"Notes").build();
        }
        return noteDatabase;
    }

    public abstract NoteDao noteDao();
}
