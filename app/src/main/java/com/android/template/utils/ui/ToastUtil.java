package com.android.template.utils.ui;

import android.support.annotation.StringRes;

public interface ToastUtil {

    void showShortToast(@StringRes int stringRes);

    void showShortToast(CharSequence text);

    void showLongToast(@StringRes int stringRes);

    void showLongToast(CharSequence text);
}
