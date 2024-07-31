package com.sun_ray.srnotes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.sun_ray.srnotes.model.Password;
import java.util.List;

@Dao
public interface PassDao {
    @Query("Select * From Passwords Order By id Desc")
    List<Password> getPass();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addPass(Password password);

    @Delete
    void deletePass(Password password);
}
