package com.android.template.utils.ui;

import android.content.Context;
import androidx.annotation.StringRes;
import android.widget.Toast;

public final class ToastUtilImpl implements ToastUtil {

    private final Context context;

    public ToastUtilImpl(final Context context) {
        this.context = context;
    }

    @Override
    public void showShortToast(@StringRes final int stringRes) {
        Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showShortToast(final CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(@StringRes final int stringRes) {
        Toast.makeText(context, stringRes, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLongToast(final CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
