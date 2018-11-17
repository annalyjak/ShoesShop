package com.example.anna.shoesshop.controller.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

public abstract class ProgressTask<T> extends AsyncTask<T, Void, Void> {

    RecyclerView.Adapter adapter;
    ProgressDialog dialog;
    Activity activity;

    ProgressTask(RecyclerView.Adapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(final Void success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        adapter.notifyDataSetChanged();
    }
}
