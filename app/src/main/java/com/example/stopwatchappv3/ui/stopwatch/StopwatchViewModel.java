package com.example.stopwatchappv3.ui.stopwatch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stopwatchappv3.data.AppRepository;
import com.example.stopwatchappv3.data.SavedStopwatch;
import com.example.stopwatchappv3.data.Stopwatch;

import java.util.List;

/**
 * has no view !!
 */
public class StopwatchViewModel extends AndroidViewModel {
    private final LiveData<List<Stopwatch>> allStopwatches;
    private final AppRepository repository;

    public StopwatchViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allStopwatches = repository.getAllStopwatches();
    }

    public void insertSavedStopwatch(Stopwatch stopwatch) {
        repository.insert(stopwatch);
    }

    public void insertSavedStopwatch(SavedStopwatch stopwatch) {
        repository.insert(stopwatch);
    }

    public void update(Stopwatch stopwatch) {
        repository.update(stopwatch);
    }

    public void delete(Stopwatch stopwatch) {
        repository.delete(stopwatch);
    }

    public LiveData<List<Stopwatch>> getAllStopwatches() {
        return allStopwatches;
    }
}
