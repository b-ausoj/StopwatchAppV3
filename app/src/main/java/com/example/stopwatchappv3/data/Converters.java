package com.example.stopwatchappv3.data;

import androidx.room.TypeConverter;

import com.example.stopwatchappv3.util.LapModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {

    /**
     * Converts the lap list to a string for storing in room database using gson
     * not best practise specially for large lists
     *
     * @param lapList list containing the laps
     * @return json string to store in the db
     */
    @TypeConverter
    public String fromList(List<LapModel> lapList) {
        if (lapList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<LapModel>>() {
        }.getType();
        return gson.toJson(lapList, type);
    }

    /**
     * Converts the json string to a list containing laps
     * attention that no other converter exists which takes a string
     *
     * @param json string from db
     * @return list containing the laps
     */
    @TypeConverter
    public List<LapModel> toList(String json) {
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<LapModel>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }
}
