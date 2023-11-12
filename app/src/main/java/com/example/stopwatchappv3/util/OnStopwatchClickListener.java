package com.example.stopwatchappv3.util;

public interface OnStopwatchClickListener {
    void onResetClick(int position);

    void onStartStopClick(int position);

    void onLapClick(int position);

    void onSaveClick(int position);

    void onDeleteClick(int position);
}
