package com.example.stopwatchappv3.ui.stopwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatchappv3.MainActivity;
import com.example.stopwatchappv3.R;
import com.example.stopwatchappv3.data.SavedStopwatch;
import com.example.stopwatchappv3.data.Stopwatch;
import com.example.stopwatchappv3.databinding.FragmentStopwatchBinding;
import com.example.stopwatchappv3.services.TimerService;
import com.example.stopwatchappv3.util.LapModel;
import com.example.stopwatchappv3.util.OnStopwatchClickListener;

import java.util.List;

public class StopwatchFragment extends Fragment implements OnStopwatchClickListener {
    private StopwatchAdapter adapter;
    private StopwatchViewModel viewModel;
    private RecyclerView recyclerView;
    private FragmentStopwatchBinding binding;
    private List<Stopwatch> allStopwatches; // necessary?
    private final BroadcastReceiver updateTime = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long time = intent.getLongExtra(TimerService.TIME_EXTRA, 0);
            //System.out.println(time);
            int position = 0;
            for (Stopwatch stopwatch : allStopwatches) {
                if (stopwatch.isRunning()) {
                    StopwatchAdapter.StopwatchViewHolder holder = (StopwatchAdapter.StopwatchViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                    if (holder != null) {
                        holder.timeTv.setText(MainActivity.getTimeStringFromInt(stopwatch.setAndGetTime(time - stopwatch.getOffset())));
                        holder.lapTv.setText(MainActivity.getTimeStringFromInt(stopwatch.setAndGetLapTime(time - stopwatch.getLapOffset())));
                    }
                }
                position++;
            }
        }
    };
    private Intent serviceIntent;
    private boolean serviceRunning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentStopwatchBinding.inflate(inflater, container, false);

        recyclerView = binding.getRoot().findViewById(R.id.stopwatch_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new StopwatchAdapter();
        adapter.setOnStopwatchClickListener(this);
        adapter.setContext(requireActivity());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(StopwatchViewModel.class);
        viewModel.getAllStopwatches().observe(getViewLifecycleOwner(), stopwatches -> {
            // update UI
            System.out.println("change in dataset observed");
            // i just change the data in the adapter
            allStopwatches = stopwatches;
            adapter.submitList(stopwatches);

            if (!serviceRunning) {
                boolean startService = false;
                for (Stopwatch stopwatchModel : allStopwatches) {
                    if (stopwatchModel.isRunning()) {
                        startService = true;
                        break;
                    }
                }
                if (startService) {
                    requireActivity().startForegroundService(serviceIntent);
                    binding.startAllBtn.setVisibility(View.GONE);
                    serviceRunning = true;
                }
            }
        });

        // Erstellt den service Intent, bestehend aus TimerService hilfsklasse
        serviceIntent = new Intent(requireActivity().getApplicationContext(), TimerService.class);
        // K.a... verbindet den Receiver von hier mit dem TimerService.
        requireActivity().registerReceiver(updateTime, new IntentFilter(TimerService.TIMER_UPDATED));
        serviceRunning = false;


        // der onClickListener für StartAll-Button
        binding.startAllBtn.setOnClickListener(view -> onStartAllClick());
        binding.addSwBtn.setOnClickListener(view -> onAddClick());

        if (allStopwatches != null) {
            boolean enFloatBtn = true;
            for (Stopwatch stopwatch : allStopwatches) {
                if (!stopwatch.isRunning()) {
                    enFloatBtn = false;
                    break;
                }
            }
            if (enFloatBtn) {
                binding.startAllBtn.setVisibility(View.VISIBLE);
            } else {
                binding.startAllBtn.setVisibility(View.GONE);
            }
        }

        return binding.getRoot();
    }

    // To stop the service when fragment is not running
    @Override
    public void onDestroy() {
        requireActivity().stopService(serviceIntent);
        serviceRunning = false;
        super.onDestroy();
    }

    private void onAddClick() {
        viewModel.insertSavedStopwatch(new Stopwatch());
    }

    @Override
    public void onDeleteClick(int position) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Do you want to delete this stopwatch?")
                .setPositiveButton("YES", (dialog, id) -> {
                    if (allStopwatches.size() > position) {
                        viewModel.delete(allStopwatches.get(position));
                        //allStopwatches.remove(position); not necessary, live data does it
                        //adapter.notifyItemRemoved(position); doing it in adapter, but must figer it out
                    }
                })
                .setNegativeButton("NO", (dialog, id) -> {
                    // User cancelled the dialog, nothing
                });
        builder.create();
        builder.show();
    }

    @Override
    public void onStartStopClick(int position) {
        Stopwatch stopwatch = allStopwatches.get(position);
        stopwatch.setReset(false);
        long time = System.currentTimeMillis();
        if (stopwatch.isRunning()) {
            // stop timer
            stopwatch.setRunning(false);
            boolean stopService = true;
            for (Stopwatch stopwatchModel : allStopwatches) {
                if (stopwatchModel.isRunning()) {
                    stopService = false;
                    break;
                }
            }
            if (stopService) {
                requireActivity().stopService(serviceIntent);
                serviceRunning = false;
            }
            stopwatch.setTime(time - stopwatch.getOffset());
            stopwatch.setLapTime(time - stopwatch.getLapOffset());
        } else {
            // start timer
            boolean startService = true;
            for (Stopwatch stopwatchModel : allStopwatches) {
                if (stopwatchModel.isRunning()) {
                    startService = false;
                    break;
                }
            }
            stopwatch.setRunning(true);
            stopwatch.setOffset(time - stopwatch.getTime());
            stopwatch.setLapOffset(time - stopwatch.getLapTime());
            if (startService) {
                requireActivity().startForegroundService(serviceIntent);
                binding.startAllBtn.setVisibility(View.GONE);
                serviceRunning = true;
            }
        }
        viewModel.update(stopwatch);
        adapter.notifyItemChanged(position); // here? sure not all need a refresh
    }

    private void onStartAllClick() {
        binding.startAllBtn.setVisibility(View.GONE);
        requireActivity().startForegroundService(serviceIntent);
        serviceRunning = true;
        int position = 0;
        for (Stopwatch stopwatch : allStopwatches) {
            long startTime = System.currentTimeMillis();
            stopwatch.setReset(false);
            stopwatch.setRunning(true);
            stopwatch.setOffset(startTime - stopwatch.getTime());
            stopwatch.setLapOffset(startTime - stopwatch.getLapTime());
            viewModel.update(stopwatch);
            adapter.notifyItemChanged(position); // okey, da nur passiert wenn alle reseted sind
            position++;
        }
    }

    @Override
    public void onLapClick(int position) {
        Stopwatch stopwatch = allStopwatches.get(position);
        LapModel lap = stopwatch.saveLap();
        //System.out.println(stopwatch.getTime());
        viewModel.update(stopwatch);

        StopwatchAdapter.StopwatchViewHolder holder = (StopwatchAdapter.StopwatchViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            // nimmt älteste lap von liste weg
            if (holder.layoutLapList.getChildCount() >= 3) {
                holder.layoutLapList.removeViewAt(2);
            }

            // fügt ein neue Lap der liste hinzu
            View lapView = LayoutInflater.from(requireActivity()).inflate(R.layout.lap_row_item, holder.layoutLapList, false);

            TextView lastLapTime = lapView.findViewById(R.id.last_lapTime_tv);
            lastLapTime.setText(MainActivity.getTimeStringFromInt(lap.getDuration()));
            TextView lastLapName = lapView.findViewById(R.id.last_lapName_tv);
            lastLapName.setText(lap.getName());

            holder.layoutLapList.addView(lapView, 0);
        }
    }

    @Override
    public void onSaveClick(int position) {
        Stopwatch stopwatch = allStopwatches.get(position);
        SavedStopwatch savedStopwatch = stopwatch.save();
        viewModel.insertSavedStopwatch(savedStopwatch);

        stopwatch.reset();
        viewModel.update(stopwatch);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onResetClick(int position) {
        Stopwatch stopwatch = allStopwatches.get(position);
        stopwatch.reset();
        viewModel.update(stopwatch);

        // shows the start all btn if no stopwatch is active
        boolean showFloatBtn = true;
        for (Stopwatch stopwatchModel : allStopwatches) {
            if (!stopwatchModel.isReset()) {
                showFloatBtn = false;
                break;
            }
        }
        if (showFloatBtn) {
            binding.startAllBtn.setVisibility(View.VISIBLE);
        }
        adapter.notifyItemChanged(position);
        // ^ist okey, da nur passiert wenn sw nicht läuft,
        // aber wird wegen livedata in adapter gleich alles aktualisiert
    }

}
