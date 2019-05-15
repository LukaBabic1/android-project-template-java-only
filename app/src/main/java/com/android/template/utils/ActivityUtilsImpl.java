package com.android.template.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.template.base.BackPropagatingFragment;
import com.annimon.stream.Optional;

import java.util.List;

import io.reactivex.functions.Function;

public final class ActivityUtilsImpl implements ActivityUtils {

    @Override
    public void addFragmentToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId) {
        if (!fragment.isAdded()) {
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    @Override
    public void addFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final int frameId, @NonNull final String tag) {
        if (!fragment.isAdded()) {
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment, tag);
            transaction.commit();
        }
    }

    @Override
    public void addFragmentWithTagToActivity(@NonNull final FragmentManager fragmentManager, @NonNull final Fragment fragment, final String tag, final int frameId,
                                             final String backStackName) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.addToBackStack(backStackName);
        transaction.commit();
    }


    /**
     * @return {@code true} is back is consumed, {@code false} otherwise
     */
    @Override
    public boolean propagateBackToTopFragment(@NonNull final FragmentManager fragmentManager) {
        return callIfPresent(findBackPropagatingFragment(fragmentManager), BackPropagatingFragment::onBack);
    }

    private boolean callIfPresent(final Optional<BackPropagatingFragment> backPropagatingFragmentOptional, final Function<BackPropagatingFragment, Boolean> action) {
        if (backPropagatingFragmentOptional.isPresent()) {
            final BackPropagatingFragment backPropagatingFragment = backPropagatingFragmentOptional.get();
            try {
                return action.apply(backPropagatingFragment);

            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    private Optional<BackPropagatingFragment> findBackPropagatingFragment(final FragmentManager fragmentManager) {
        final List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) {
            return Optional.empty();
        }
        for (int i = fragments.size() - 1; i >= 0; i--) {
            final Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isVisible() && fragment.getUserVisibleHint()) {
                if (fragment instanceof BackPropagatingFragment) {
                    return Optional.of(((BackPropagatingFragment) fragment));
                }
                break;
            }
        }
        return Optional.empty();
    }
}