package com.android.template.utils.ui;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public interface AlertDialogFactory {

    DialogInterface.OnClickListener adaptToRunnable(Runnable runnable);

    DialogInterface.OnClickListener getEmptyListener();
}
