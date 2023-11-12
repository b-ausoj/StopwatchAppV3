package com.example.stopwatchappv3.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.stopwatchappv3.util.LapModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Stopwatch {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long time;
    private long offset;
    private long lapTime;
    private long lapOffset;
    private long lapCount;
    private boolean running;
    private boolean reset;
    private List<LapModel> lapList;

    public Stopwatch() {
        this.time = 0;
        this.offset = 0;
        this.lapTime = 0;
        this.lapOffset = 0;
        this.lapCount = 0;
        this.running = false;
        this.reset = true;
        this.lapList = new ArrayList<>();
    }

    public LapModel saveLap() {
        lapOffset += lapTime;
        LapModel lap = new LapModel(++lapCount, "Lap" + lapCount, lapTime, time);
        lapList.add(lap);
        lapTime = 0;
        return lap;
    }

    public void reset() {
        time = 0;
        offset = 0;
        lapTime = 0;
        lapOffset = 0;
        lapCount = 0;
        reset = true;
        running = false;
        lapList = new ArrayList<>();
    }

    public SavedStopwatch save() {
        saveLap();
        return new SavedStopwatch("Stopwatch " + id, time, Calendar.getInstance().getTime(), lapList);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long setAndGetTime(long time) {
        this.time = time;
        return time;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLapTime() {
        return lapTime;
    }

    public void setLapTime(long lapTime) {
        this.lapTime = lapTime;
    }

    public long getLapOffset() {
        return lapOffset;
    }

    public void setLapOffset(long lapOffset) {
        this.lapOffset = lapOffset;
    }

    public long setAndGetLapTime(long lapTime) {
        this.lapTime = lapTime;
        return lapTime;
    }

    public long getLapCount() {
        return lapCount;
    }

    public void setLapCount(long lapCount) {
        this.lapCount = lapCount;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public List<LapModel> getLapList() {
        return lapList;
    }

    public void setLapList(List<LapModel> lapList) {
        this.lapList = lapList;
    }
}
