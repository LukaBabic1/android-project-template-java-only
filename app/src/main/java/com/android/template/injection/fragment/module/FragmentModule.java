package com.android.template.injection.fragment.module;

import androidx.fragment.app.FragmentManager;

import com.android.template.injection.fragment.DaggerFragment;
import com.android.template.injection.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public final class FragmentModule {

    private final DaggerFragment fragment;

    public FragmentModule(final DaggerFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    FragmentManager provideFragmentManager() {
        return fragment.getChildFragmentManager();
    }
}
