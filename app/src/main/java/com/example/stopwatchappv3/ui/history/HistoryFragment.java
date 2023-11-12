package com.example.stopwatchappv3.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatchappv3.R;
import com.example.stopwatchappv3.databinding.FragmentHistoryBinding;

/**
 * The history should show all saved stopwatches in a card in a recycler view
 * It should only show a preview of the stopwatch and with tapping on the stopwatch ..
 * we should see a detail view with all laps (in a new fragment?)
 * TODO: https://www.youtube.com/watch?v=reSPN7mgshI weiter machen für recycler view
 */
public class HistoryFragment extends Fragment {
    private HistoryViewModel viewModel;
    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        recyclerView = binding.getRoot().findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new HistoryAdapter();
        adapter.setContext(requireActivity());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        viewModel.getAllSavedStopwatches().observe(getViewLifecycleOwner(), savedStopwatches -> {
            // update UI
            adapter.setAllSavedStopwatches(savedStopwatches);
        });

        return binding.getRoot();
    }
}
