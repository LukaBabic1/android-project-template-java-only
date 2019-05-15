package com.android.template.application;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.android.template.injection.application.ApplicationComponent;
import com.android.template.injection.factory.ComponentFactory;
import com.android.template.injection.user.UserComponent;
import com.android.template.utils.StethoInitializer;

import javax.inject.Inject;

import template.android.com.device.connectivity.ConnectivityReceiver;

public final class ExampleApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;
    private UserComponent userComponent;

    @Inject
    ConnectivityReceiver connectivityReceiver;

    @Inject
    StethoInitializer stethoInitializer;

    public static ExampleApplication from(final Context context) {
        return ExampleApplication.class.cast(context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
        initUserComponent();
        injectMe();
        initStetho();
    }

    private void initStetho() {
        stethoInitializer.initialize();
    }

    private void initApplicationComponent() {
        applicationComponent = ComponentFactory.createApplicationComponent(this);
    }

    private void initUserComponent() {
        userComponent = ComponentFactory.createUserComponent(applicationComponent);
    }

    private void injectMe() {
        applicationComponent.inject(this);
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }
}
