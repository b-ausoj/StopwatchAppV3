package com.example.stopwatchappv3.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StopwatchDAO {

    @Insert
    void insert(Stopwatch stopwatch);

    @Update
    void update(Stopwatch stopwatch);

    @Delete
    void delete(Stopwatch stopwatch);

    @Query("SELECT * FROM stopwatch ORDER BY id ASC")
    LiveData<List<Stopwatch>> getAllStopwatches();
}
