package com.example.stopwatchappv3.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private final StopwatchDAO stopwatchDAO;
    private final SavedStopwatchDAO savedStopwatchDAO;
    private final LiveData<List<Stopwatch>> allStopwatches;
    private final LiveData<List<SavedStopwatch>> allSavedStopwatches;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        stopwatchDAO = database.stopwatchDAO();
        savedStopwatchDAO = database.savedStopwatchDAO();

        allStopwatches = stopwatchDAO.getAllStopwatches();
        allSavedStopwatches = savedStopwatchDAO.getAllSavedStopwatches();
    }

    public void insert(Stopwatch stopwatch) {
        new Thread(() -> stopwatchDAO.insert(stopwatch)).start();
    }

    public void insert(SavedStopwatch stopwatch) {
        new Thread(() -> savedStopwatchDAO.insert(stopwatch)).start();
    }

    public void update(Stopwatch stopwatch) {
        new Thread(() -> stopwatchDAO.update(stopwatch)).start();
    }

    public void update(SavedStopwatch stopwatch) {
        new Thread(() -> savedStopwatchDAO.update(stopwatch)).start();
    }

    public void delete(Stopwatch stopwatch) {
        new Thread(() -> stopwatchDAO.delete(stopwatch)).start();
    }

    public void delete(SavedStopwatch stopwatch) {
        new Thread(() -> savedStopwatchDAO.delete(stopwatch)).start();
    }

    public LiveData<List<Stopwatch>> getAllStopwatches() {
        return allStopwatches;
    }

    public LiveData<List<SavedStopwatch>> getAllSavedStopwatches() {
        return allSavedStopwatches;
    }
}
