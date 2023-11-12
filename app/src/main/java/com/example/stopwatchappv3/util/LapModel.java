package com.example.stopwatchappv3.util;

/**
 * This is a helper class to store a lap
 */
public class LapModel {
    private final long id;
    private final String name;
    private final long duration;
    private final long timePoint;

    public LapModel(long id, String name, long duration, long timePoint) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.timePoint = timePoint;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public long getTimePoint() {
        return timePoint;
    }
}
