package com.example.stopwatchappv3.ui.stopwatch;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatchappv3.MainActivity;
import com.example.stopwatchappv3.R;
import com.example.stopwatchappv3.data.Stopwatch;
import com.example.stopwatchappv3.util.LapModel;
import com.example.stopwatchappv3.util.OnStopwatchClickListener;

public class StopwatchAdapter extends ListAdapter<Stopwatch, StopwatchAdapter.StopwatchViewHolder> {
    private OnStopwatchClickListener onStopwatchClickListener;
    private Context context;

    public StopwatchAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Stopwatch> DIFF_CALLBACK = new DiffUtil.ItemCallback<Stopwatch>() {
        @Override
        public boolean areItemsTheSame(@NonNull Stopwatch oldItem, @NonNull Stopwatch newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Stopwatch oldItem, @NonNull Stopwatch newItem) {
            return true;
        }
    };

    public void setOnStopwatchClickListener(OnStopwatchClickListener onStopwatchClickListener) {
        this.onStopwatchClickListener = onStopwatchClickListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // erstellt das view
    @NonNull
    @Override
    public StopwatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stopwatch_row_item, parent, false);
        return new StopwatchViewHolder(view, onStopwatchClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull StopwatchViewHolder holder, int position) {
        Stopwatch stopwatch = getItem(position);
        // text view for current time and lap time
        holder.idTv.setText("" + stopwatch.getId());
        holder.timeTv.setText(MainActivity.getTimeStringFromInt(stopwatch.getTime()));
        holder.lapTv.setText(MainActivity.getTimeStringFromInt(stopwatch.getLapTime()));
        // list view for past lap times
        holder.layoutLapList.removeAllViews();

        // ugly but only three last laps have space
        // add the last laps to the stopwatch card
        if (stopwatch.getLapCount() >= 3) {
            LapModel lap = stopwatch.getLapList().get((int) stopwatch.getLapCount() - 3);
            View lapView = LayoutInflater.from(context).inflate(R.layout.lap_row_item, holder.layoutLapList, false);

            TextView lastLapTime = lapView.findViewById(R.id.last_lapTime_tv);
            lastLapTime.setText(MainActivity.getTimeStringFromInt(lap.getDuration()));
            TextView lastLapName = lapView.findViewById(R.id.last_lapName_tv);
            lastLapName.setText(lap.getName());

            holder.layoutLapList.addView(lapView, 0);
        }
        if (stopwatch.getLapCount() >= 2) {
            LapModel lap = stopwatch.getLapList().get((int) stopwatch.getLapCount() - 2);
            View lapView = LayoutInflater.from(context).inflate(R.layout.lap_row_item, holder.layoutLapList, false);

            TextView lastLapTime = lapView.findViewById(R.id.last_lapTime_tv);
            lastLapTime.setText(MainActivity.getTimeStringFromInt(lap.getDuration()));
            TextView lastLapName = lapView.findViewById(R.id.last_lapName_tv);
            lastLapName.setText(lap.getName());

            holder.layoutLapList.addView(lapView, 0);
        }
        if (stopwatch.getLapCount() >= 1) {
            LapModel lap = stopwatch.getLapList().get((int) stopwatch.getLapCount() - 1);
            View lapView = LayoutInflater.from(context).inflate(R.layout.lap_row_item, holder.layoutLapList, false);

            TextView lastLapTime = lapView.findViewById(R.id.last_lapTime_tv);
            lastLapTime.setText(MainActivity.getTimeStringFromInt(lap.getDuration()));
            TextView lastLapName = lapView.findViewById(R.id.last_lapName_tv);
            lastLapName.setText(lap.getName());

            holder.layoutLapList.addView(lapView, 0);
        }

        // buttons activated or not
        holder.resetBtn.setEnabled(!(stopwatch.isReset() || stopwatch.isRunning()));
        if (stopwatch.isReset()) {
            holder.resetBtn.setBackgroundColor(context.getColor(R.color.disable));
            holder.lapBtn.setBackgroundColor(context.getColor(R.color.disable));
            holder.saveBtn.setBackgroundColor(context.getColor(R.color.disable));
            holder.deleteBtn.setBackgroundColor(context.getColor(R.color.delete));

            holder.startStopBtn.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_start), null, null, null);
            holder.startStopBtn.setBackgroundColor(context.getColor(R.color.start));
            holder.startStopBtn.setText(context.getText(R.string.start));
        } else if (stopwatch.isRunning()) {
            holder.resetBtn.setBackgroundColor(context.getColor(R.color.disable));
            holder.lapBtn.setBackgroundColor(context.getColor(R.color.lap));
            holder.saveBtn.setBackgroundColor(context.getColor(R.color.disable));
            holder.deleteBtn.setBackgroundColor(context.getColor(R.color.disable));

            holder.startStopBtn.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_stop), null, null, null);
            holder.startStopBtn.setBackgroundColor(context.getColor(R.color.stop));
            holder.startStopBtn.setText(context.getText(R.string.stop));
        } else {
            holder.resetBtn.setBackgroundColor(context.getColor(R.color.reset));
            holder.lapBtn.setBackgroundColor(context.getColor(R.color.disable));
            holder.saveBtn.setBackgroundColor(context.getColor(R.color.save));
            holder.deleteBtn.setBackgroundColor(context.getColor(R.color.delete));

            holder.startStopBtn.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_resume), null, null, null);
            holder.startStopBtn.setBackgroundColor(context.getColor(R.color.resume));
            holder.startStopBtn.setText(context.getText(R.string.resume));
        }
        holder.lapBtn.setEnabled(stopwatch.isRunning());
        holder.saveBtn.setEnabled(!stopwatch.isRunning() && !stopwatch.isReset());
        holder.deleteBtn.setEnabled(!stopwatch.isRunning());

        // make touch area of button bigger
        expandTouchArea(holder.startStopBtn);
    }

    // Funktioniert nur für ein Button im Parentview
    private void expandTouchArea(Button button) {
        View parent = (View) button.getParent();
        parent.post(() -> {
            Rect delegateArea = new Rect();
            button.getHitRect(delegateArea);
            delegateArea.top -= 40;
            delegateArea.bottom += 40;
            parent.setTouchDelegate(new TouchDelegate(delegateArea, button));
        });
    }

    public static class StopwatchViewHolder extends RecyclerView.ViewHolder {
        public TextView idTv;
        public TextView timeTv;
        public TextView lapTv;
        public LinearLayout layoutLapList;
        public Button resetBtn;
        public Button startStopBtn;
        public Button lapBtn;
        public Button saveBtn;
        public Button deleteBtn;

        public StopwatchViewHolder(@NonNull View itemView, OnStopwatchClickListener onStopwatchClickListener) {
            super(itemView);
            idTv = itemView.findViewById(R.id.id_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
            lapTv = itemView.findViewById(R.id.lap_tv);
            layoutLapList = itemView.findViewById(R.id.layout_lapList);
            resetBtn = itemView.findViewById(R.id.reset_btn);
            startStopBtn = itemView.findViewById(R.id.startStop_btn);
            lapBtn = itemView.findViewById(R.id.lap_btn);
            saveBtn = itemView.findViewById(R.id.save_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

            resetBtn.setOnClickListener(view -> {
                if (onStopwatchClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onStopwatchClickListener.onResetClick(position);
                    }
                }
            });
            startStopBtn.setOnClickListener(view -> {
                if (onStopwatchClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onStopwatchClickListener.onStartStopClick(position);
                    }
                }
            });
            lapBtn.setOnClickListener(view -> {
                if (onStopwatchClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onStopwatchClickListener.onLapClick(position);
                    }
                }
            });
            saveBtn.setOnClickListener(view -> {
                if (onStopwatchClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onStopwatchClickListener.onSaveClick(position);
                    }
                }
            });
            deleteBtn.setOnClickListener(view -> {
                if (onStopwatchClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onStopwatchClickListener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}
