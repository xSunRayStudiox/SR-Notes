package com.sun_ray.srnotes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sun_ray.srnotes.model.Url;

import java.util.List;

@Dao
public interface UrlDao {
    @Query("Select * From Urls Order By Date Desc")
    List<Url> getUrls();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUrl(Url url);

    @Delete
    void deleteUrl(Url url);
}
