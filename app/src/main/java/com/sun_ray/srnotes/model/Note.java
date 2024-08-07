package com.sun_ray.srnotes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Note{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Content")
    private String content;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "ColorIndex")
    private int colorIndex = 0;

    public Note(int id, String title, String content, String date, int colorIndex) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.colorIndex = colorIndex;
    }

    @Ignore
    public Note(String title, String content, String date, int colorIndex) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.colorIndex = colorIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }
}
