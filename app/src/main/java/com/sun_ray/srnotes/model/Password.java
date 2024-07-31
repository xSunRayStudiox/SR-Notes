package com.sun_ray.srnotes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Passwords")
public class Password{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Headings")
    private String heading;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "UserId")
    private String userId;

    @ColumnInfo(name = "Pass")
    private String pass;

    public Password(int id, String heading, String date, String userId, String pass) {
        this.id = id;
        this.heading = heading;
        this.date = date;
        this.userId = userId;
        this.pass = pass;
    }

    @Ignore
    public Password(String heading, String date, String userId, String pass) {
        this.heading = heading;
        this.date = date;
        this.userId = userId;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
