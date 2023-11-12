package com.example.stopwatchappv3.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedStopwatchDAO {

    @Insert
    void insert(SavedStopwatch stopwatch);

    @Update
    void update(SavedStopwatch stopwatch);

    @Delete
    void delete(SavedStopwatch stopwatch);

    @Query("SELECT * FROM savedstopwatch ORDER BY id DESC")
    LiveData<List<SavedStopwatch>> getAllSavedStopwatches();
}
