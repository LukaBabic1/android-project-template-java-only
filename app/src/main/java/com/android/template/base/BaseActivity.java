package com.android.template.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.template.injection.activity.DaggerActivity;
import com.android.template.utils.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import template.android.com.domain.utils.collection.ListUtils;

public abstract class BaseActivity extends DaggerActivity {

    @Inject
    protected FragmentManager fragmentManager;

    @Inject
    ActivityUtils activityUtils;

    @Inject
    ListUtils listUtils;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Integer layoutResourceId = getLayoutResourceId();
        if (layoutResourceId != null) {
            setContentView(layoutResourceId);
            bindViews();
        }

        startPresenter();
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    protected abstract Integer getLayoutResourceId();

    private void startPresenter() {
        getPresenter().start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().activate();
    }

    @Override
    protected void onPause() {
        getPresenter().deactivate();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (!activityUtils.propagateBackToTopFragment(fragmentManager)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            getPresenter().destroy();
            clearFragments();
        }
        super.onDestroy();
    }

    @SuppressWarnings("Convert2streamapi")
    private void clearFragments() {
        final List<Fragment> fragments = fragmentManager.getFragments();
        if (!listUtils.isEmpty(fragments)) {
            propagateOnPreDestroyToFragments(fragments);
        }
    }

    private void propagateOnPreDestroyToFragments(final List<Fragment> fragments) {
        for (final Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                BaseFragment.class.cast(fragment).onPreDestroy();
            }
        }
    }

    protected abstract ScopedPresenter getPresenter();
}
