package com.android.template.injection.activity.module;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import com.android.template.injection.qualifier.ForActivity;
import com.android.template.injection.activity.DaggerActivity;
import com.android.template.injection.scope.ActivityScope;
import com.android.template.utils.animation.AnimationFactory;
import com.android.template.utils.animation.AnimationFactoryImpl;
import com.android.template.utils.animation.AnimationInfoProvider;
import com.android.template.utils.animation.AnimationInfoProviderImpl;
import com.android.template.utils.ui.AlertDialogFactory;
import com.android.template.utils.ui.AlertDialogFactoryImpl;
import com.android.template.ui.Router;
import com.android.template.ui.RouterImpl;
import com.android.template.utils.ActivityUtils;
import com.android.template.utils.ActivityUtilsImpl;
import com.android.template.utils.ui.ImageLoader;
import com.android.template.utils.ui.ImageLoaderImpl;
import com.android.template.utils.ui.KeyboardUtils;
import com.android.template.utils.ui.KeyboardUtilsImpl;
import com.android.template.utils.ui.ToastUtil;
import com.android.template.utils.ui.ToastUtilImpl;

import dagger.Module;
import dagger.Provides;
import template.android.com.domain.utils.collection.ListUtils;

@Module
public final class ActivityModule {

    private final DaggerActivity activity;

    public ActivityModule(final DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    ActivityUtils provideActivityUtils() {
        return new ActivityUtilsImpl();
    }

    @Provides
    @ActivityScope
    AlertDialogFactory provideAlertDialogFactory(@ForActivity final Context context) {
        return new AlertDialogFactoryImpl(context);
    }

    @Provides
    @ActivityScope
    AnimationFactory provideAnimationProvider(@ForActivity final Context context) {
        return new AnimationFactoryImpl(context);
    }

    @Provides
    @ActivityScope
    AnimationInfoProvider provideAnimationInfoProvider(final Resources resources) {
        return new AnimationInfoProviderImpl(resources);
    }

    @Provides
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    ImageLoader provideImageLoader(@ForActivity final Context context) {
        return new ImageLoaderImpl(context);
    }

    @Provides
    @ActivityScope
    InputMethodManager provideInputMethodManager(@ForActivity final Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides
    @ActivityScope
    KeyboardUtils provideKeyboardUtils(final InputMethodManager inputMethodManager) {
        return new KeyboardUtilsImpl(inputMethodManager);
    }

    @Provides
    @ActivityScope
    LayoutInflater provideLayoutInflater(@ForActivity final Context context) {
        return LayoutInflater.from(context);
    }

    @Provides
    @ActivityScope
    Router provideRouter(final FragmentManager fragmentManager, final ListUtils listUtils) {
        return new RouterImpl(activity, fragmentManager, listUtils);
    }

    @Provides
    @ActivityScope
    ToastUtil provideToastUtil(@ForActivity final Context context) {
        return new ToastUtilImpl(context);
    }

    public interface Exposes {

        ActivityUtils activityUtils();

        AlertDialogFactory alertDialogFactory();

        AnimationFactory animationFactory();

        AnimationInfoProvider animationInfoProvider();

        @ForActivity
        Context provideActivityContext();

        ImageLoader imageLoader();

        KeyboardUtils keyboardUtils();

        LayoutInflater layoutInflater();

        Router router();

        ToastUtil toastUtil();
    }
}
