package com.android.template.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface ActivityUtils {

    void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId);

    void addFragmentWithTagToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, String tag);

    void addFragmentWithTagToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, int frameId, String backStackName);

    boolean propagateBackToTopFragment(@NonNull FragmentManager fragmentManager);
}

