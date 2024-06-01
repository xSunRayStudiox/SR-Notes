package com.sun_ray.srnotes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Notes")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "Content")
    private String content;
    @ColumnInfo(name = "Date")
    private String date;
    @ColumnInfo(name = "ImagePath")
    private String imagePath;
    @ColumnInfo(name = "WebLink")
    private String webLink;
    @ColumnInfo(name = "Color")
    private int color = -1;

//    public Note(int id, String title, String content, String date, String imagePath, String webLink, int color) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.imagePath = imagePath;
//        this.webLink = webLink;
//        this.color = color;
//    }
//
//    @Ignore
//    public Note(String title, String content, String date, String imagePath, String webLink, int color) {
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.imagePath = imagePath;
//        this.webLink = webLink;
//        this.color = color;
//    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return title+ " : " +date;
    }
}
