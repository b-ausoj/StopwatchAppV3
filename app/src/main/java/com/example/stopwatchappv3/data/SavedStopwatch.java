package com.example.stopwatchappv3.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.stopwatchappv3.util.LapModel;

import java.util.Date;
import java.util.List;

@Entity
public class SavedStopwatch {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private long totalTime;
    private Date date; // to know when the sw was saved
    private List<LapModel> lapList;

    public SavedStopwatch(String name, long totalTime, Date date, List<LapModel> lapList) {
        this.name = name;
        this.totalTime = totalTime;
        this.date = date;
        this.lapList = lapList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<LapModel> getLapList() {
        return lapList;
    }

    public void setLapList(List<LapModel> lapList) {
        this.lapList = lapList;
    }
}
