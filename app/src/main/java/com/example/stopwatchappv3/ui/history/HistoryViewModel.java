package com.example.stopwatchappv3.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stopwatchappv3.data.AppRepository;
import com.example.stopwatchappv3.data.SavedStopwatch;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final AppRepository repository;
    private final LiveData<List<SavedStopwatch>> allSavedStopwatches;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allSavedStopwatches = repository.getAllSavedStopwatches();
    }

    public void update(SavedStopwatch stopwatch) {
        repository.update(stopwatch);
    }

    public void delete(SavedStopwatch stopwatch) {
        repository.delete(stopwatch);
    }

    public LiveData<List<SavedStopwatch>> getAllSavedStopwatches() {
        return allSavedStopwatches;
    }

}
