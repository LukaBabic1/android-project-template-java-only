package template.android.com.device.device;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

import template.android.com.domain.device.PermissionInformation;

public final class PermissionInformationImpl implements PermissionInformation {

    private final Context context;

    public PermissionInformationImpl(final Context context) {
        this.context = context;
    }

    @Override
    public boolean hasReadPhoneStatePermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }
}
