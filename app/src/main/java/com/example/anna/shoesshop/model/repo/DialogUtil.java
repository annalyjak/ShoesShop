package com.example.anna.shoesshop.model.repo;

import android.app.Activity;
import android.app.AlertDialog;

public abstract class DialogUtil {

    public static void createDialog(Activity activity, String title, String message) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.cancel())
                .show();
    }
}
