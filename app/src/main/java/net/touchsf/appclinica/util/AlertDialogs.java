package net.touchsf.appclinica.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import net.touchsf.appclinica.R;

public class AlertDialogs {

    public static void showMessage(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.accept, null)
                .show();
    }

    public static void showMessage(Context context, int resId) {
        new AlertDialog.Builder(context)
                .setMessage(context.getString(resId))
                .setPositiveButton(R.string.accept, null)
                .show();
    }

    public static void openDialog(@NonNull Dialog dialog) {
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
