package com.android.template.base;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.android.template.injection.application.module.ThreadingModule;
import com.android.template.ui.Router;
import com.android.template.utils.actionqueue.ViewActionQueue;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import template.android.com.device.connectivity.ConnectivityReceiver;

public abstract class BasePresenter<View extends BaseView> implements ScopedPresenter {

    @Inject
    protected ConnectivityReceiver connectivityReceiver;

    @Inject
    protected Router router;

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    protected Scheduler mainThreadScheduler;

    @Inject
    @Named(ThreadingModule.BACKGROUND_SCHEDULER)
    protected Scheduler backgroundScheduler;

    private final WeakReference<View> viewReference;

    private Disposable viewActionsSubscription;

    protected final ViewActionQueue<View> viewActionQueue = new ViewActionQueue<>();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final CompositeDisposable permissionSubscriptions = new CompositeDisposable();

    public BasePresenter(final View view) {
        viewReference = new WeakReference<>(view);
    }

    @Override
    public void start() {

    }

    @Override
    @CallSuper
    public void activate() {
        viewActionsSubscription = viewActionQueue.viewActionsObservable()
                                                 .observeOn(mainThreadScheduler)
                                                 .subscribe(this::doIfViewNotNull);
        viewActionQueue.resume();

        subscribeToConnectivityChange();
    }

//    protected Observable.Operator<Consumer<View>, Consumer<View>> getViewActionBackPressureStrategy() {
//        return OperatorOnBackpressureBuffer.instance();
//    }

    protected final void onViewAction(final Consumer<View> viewAction) {
        viewActionQueue.scheduleViewAction(viewAction);
    }

    private void subscribeToConnectivityChange() {
        addDisposable(connectivityReceiver.getConnectivityStatus()
                                          .subscribeOn(backgroundScheduler)
                                          .observeOn(mainThreadScheduler)
                                          .subscribe(this::onConnectivityChange, this::logError));
    }

    protected void onConnectivityChange(final boolean isConnected) {
        // Template method
    }

    @Override
    @CallSuper
    public void deactivate() {
        viewActionQueue.pause();
        viewActionsSubscription.dispose();
        disposables.clear();
    }

    @Override
    public void stop() {
        permissionSubscriptions.clear();
    }

    @Override
    @CallSuper
    public void destroy() {
        viewActionQueue.destroy();
        disposables.clear();
    }

    @Override
    public void back() {
        router.goBack();
    }

    protected void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    protected void addPermissionSubscription(final Disposable disposable) {
        permissionSubscriptions.add(disposable);
    }

    protected final void doIfConnectedToInternet(final Action ifConnected, final Action ifNotConnected) {
        addDisposable(connectivityReceiver.isConnected()
                                          .subscribeOn(backgroundScheduler)
                                          .observeOn(mainThreadScheduler)
                                          .subscribe(isConnected -> onConnectedToInternet(isConnected, ifConnected, ifNotConnected),
                                                       this::logError));
    }

    protected final void observeInternetConnection(final Action ifConnected, final Action ifNotConnected) {
        addDisposable(connectivityReceiver.getConnectivityStatus()
                                          .distinctUntilChanged()
                                          .subscribeOn(backgroundScheduler)
                                          .observeOn(mainThreadScheduler)
                                          .subscribe(isConnected -> onConnectedToInternet(isConnected, ifConnected, ifNotConnected),
                                                       this::logError));
    }

    private void onConnectedToInternet(final boolean isConnected, final Action ifConnected, final Action ifNotConnected) {
        try {
            (isConnected ? ifConnected : ifNotConnected).run();

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void logError(final Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
        }
    }

    protected void doIfViewNotNull(final Consumer<View> whenViewNotNull) {
        final View view = getNullableView();
        if (view != null) {
            try {
                whenViewNotNull.accept(view);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected <R> R getIfViewNotNull(final Function<View, R> whenViewNotNull, final R defaultValue) {
        final View view = getNullableView();
        if (view != null) {
            try {
                return whenViewNotNull.apply(view);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
        return defaultValue;
    }

    @Nullable
    protected View getNullableView() {
        return viewReference.get();
    }
}

