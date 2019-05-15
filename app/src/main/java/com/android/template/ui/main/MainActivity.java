package com.android.template.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.template.R;
import com.android.template.base.BaseActivity;
import com.android.template.base.ScopedPresenter;
import com.android.template.injection.activity.ActivityComponent;
import com.android.template.utils.ui.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import template.android.com.data.DataJavaInvoker;
import template.android.com.data.DataKotlinInvoker;
import template.android.com.device.DeviceJavaInvoker;
import template.android.com.device.DeviceKotlinInvoker;
import template.android.com.domain.DomainJavaInvoker;
import template.android.com.domain.DomainKotlinInvoker;

public final class MainActivity extends BaseActivity implements MainContract.View {

    private static final String CHANNEL_ID = "default";

    @BindView(R.id.activity_main_text_view)
    TextView textView;

    @BindView(R.id.activity_main_image_view)
    ImageView imageView;

    @BindView(R.id.activity_main_edit_text)
    EditText editText;

    @Inject
    MainContract.Presenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Inject
    NotificationManager notificationManager;

    private int notificationId = 100;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To ensure all modules are well connected.
        new DataJavaInvoker().invoke();
        new DataKotlinInvoker().invoke();

        new DeviceJavaInvoker().invoke();
        new DeviceKotlinInvoker().invoke();

        new DomainJavaInvoker().invoke();
        new DomainKotlinInvoker().invoke();

        textView.setText("bla");

        editText.setText("bla");

        imageLoader.loadImage("http://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg",
                              imageView);

        presenter.init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Test", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test purposes");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }

        for (int i = 0; i < 3; i++) {
            notificationManager.notify(notificationId++, new NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher)
                                                                                                         .setContentTitle("Title")
                                                                                                         .setContentText("Text")
                                                                                                         .setColorized(true)
                                                                                                         .setColor(Color.RED)
                                                                                                         .build());
        }
    }

    @Override
    protected void inject(final ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected Integer getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected ScopedPresenter getPresenter() {
        return presenter;
    }

    @OnClick(R.id.activity_main_text_view)
    void onTextViewClicked() {
        textView.setText(editText.getText().toString());
    }
}
