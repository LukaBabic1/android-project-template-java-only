package com.android.template.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.FragmentManager;

import com.android.template.R;
import com.android.template.ui.main.MainActivity;

import template.android.com.domain.utils.collection.ListUtils;

public final class RouterImpl implements Router {

    public static final int CONTAINER_ID = R.id.activity_main_fragment_container;

    private final Activity activity;
    private final FragmentManager fragmentManager;
    private final ListUtils listUtils;

    public RouterImpl(final Activity activity, final FragmentManager fragmentManager, final ListUtils listUtils) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.listUtils = listUtils;
    }

    @Override
    public void showMainScreen() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    public boolean showPageInExternalBrowser(final String url) {

        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        if (canIntentBeResolved(intent)) {
            activity.startActivity(intent);
            return true;
        }

        return false;
    }

    private boolean canIntentBeResolved(final Intent intent) {
        return !listUtils.isEmpty(activity.getPackageManager().queryIntentActivities(intent, 0));
    }

    public void goBack() {
        if (areThereFragmentsOnBackStack()) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }

    private boolean areThereFragmentsOnBackStack() {
        return fragmentManager.getBackStackEntryCount() > 0;
    }
}
