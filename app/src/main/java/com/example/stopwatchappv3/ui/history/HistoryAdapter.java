package com.example.stopwatchappv3.ui.history;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatchappv3.MainActivity;
import com.example.stopwatchappv3.R;
import com.example.stopwatchappv3.data.SavedStopwatch;
import com.example.stopwatchappv3.util.LapModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private List<SavedStopwatch> allSavedStopwatches = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_row_item, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        SavedStopwatch stopwatch = allSavedStopwatches.get(position);
        holder.nameTv.setText(stopwatch.getName());
        holder.dateTv.setText(new SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.GERMAN).format(stopwatch.getDate()));

        for (LapModel lap : stopwatch.getLapList()) {
            TextView textView = new TextView(context);
            textView.setText(String.format(Locale.getDefault(), "%s:      %s      %s", lap.getName(), MainActivity.getTimeStringFromInt(lap.getDuration()), MainActivity.getTimeStringFromInt(lap.getTimePoint())));
            textView.setTextColor(context.getColor(R.color.text));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            textView.setLayoutParams(params);

            holder.layoutLapList.addView(textView);
        }
        TextView textView = new TextView(context);
        textView.setText(String.format(Locale.getDefault(), "Total time:      %s", MainActivity.getTimeStringFromInt(stopwatch.getTotalTime())));
        textView.setTextColor(context.getColor(R.color.text));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        textView.setLayoutParams(params);

        holder.layoutLapList.addView(textView);
    }

    @Override
    public int getItemCount() {
        return allSavedStopwatches.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAllSavedStopwatches(List<SavedStopwatch> allSavedStopwatches) {
        this.allSavedStopwatches = allSavedStopwatches;
        notifyDataSetChanged(); /** TODO: to be changed in part 10 */
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public TextView dateTv;
        public LinearLayout layoutLapList;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.stopwatch_name_tv);
            dateTv = itemView.findViewById(R.id.stopwatch_date_tv);
            layoutLapList = itemView.findViewById(R.id.lapList_layout);
        }
    }
}
