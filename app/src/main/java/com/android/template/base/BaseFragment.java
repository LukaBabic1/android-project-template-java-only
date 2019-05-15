package com.android.template.base;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.template.injection.fragment.DaggerFragment;
import com.annimon.stream.Optional;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment extends DaggerFragment implements BackPropagatingFragment {

    @Inject
    protected Resources resources;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return bindViews(inflater.inflate(getLayoutResourceId(), container, false));
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

    private View bindViews(final View view) {
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStop() {
        getPresenter().stop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().activate();
    }

    @Override
    public void onPause() {
        getPresenter().deactivate();
        super.onPause();
    }

    public void onPreDestroy() {
        Optional.ofNullable(getPresenter())
                .ifPresent(ScopedPresenter::destroy);
    }

    @Override
    public void onDestroy() {
        onPreDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onBack() {
        getPresenter().back();
        return true;
    }

    protected final void showShortToast(@StringRes final int stringResourceId) {
        showToast(stringResourceId, Toast.LENGTH_SHORT);
    }

    protected final void showLongToast(@StringRes final int stringResourceId) {
        showToast(stringResourceId, Toast.LENGTH_LONG);
    }

    private void showToast(@StringRes final int stringResourceId, final int length) {
        Optional.ofNullable(getContext())
                .ifPresent(context -> Toast.makeText(context, stringResourceId, length).show());
    }

    public abstract ScopedPresenter getPresenter();
}
