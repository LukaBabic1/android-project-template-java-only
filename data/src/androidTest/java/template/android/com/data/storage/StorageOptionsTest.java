package template.android.com.data.storage;

import android.content.Context;
import android.os.Environment;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import template.android.com.data.R;

@RunWith(AndroidJUnit4.class)
public final class StorageOptionsTest {

//    @Rule
//    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext()
                                         .getApplicationContext();
    }

    @Test
    public void testStorageOptions() throws Exception {

        writeToDirectory(context.getFilesDir(), "getFilesDir_file", "getFilesDir");
        writeToDirectory(context.getDir("getDir", Context.MODE_PRIVATE), "getDir_file", "getDir");
        writeToDirectory(context.getCodeCacheDir(), "codeCacheDir_file", "getCodeCacheDir");
        writeToDirectory(context.getCacheDir(), "getCacheDir_file", "getCacheDir");
        writeToDirectory(context.getExternalFilesDir(null), "getExternalFilesDir_null_file", "getExternalFilesDir_null");
        writeToDirectory(context.getExternalFilesDir("getExternalFilesDir"), "getExternalFilesDir_file", "getExternalFilesDir");
        writeToDirectory(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "getExternalFilesDir_documents_file", "getExternalFilesDir_documents");
        writeToDirectory(Environment.getExternalStorageDirectory(), "getExternalStorageDirectory_file", "getExternalStorageDirectory");
        writeToDirectory(Environment.getDataDirectory(), "getDataDirectory_file", "getDataDirectory");
    }

    public void writePictureToDirectory(final File file) throws IOException {

        final InputStream is = context.getResources().openRawResource(R.drawable.ic_launcher);
        final OutputStream os = new FileOutputStream(file);

        final byte[] data = new byte[is.available()];

        is.read(data);
        os.write(data);

        is.close();
        os.close();
    }

    private void writeToDirectory(File parent, String fileName, String tag) throws Exception {

        BufferedWriter writer;

        if (!parent.canWrite()) {
            Log.d("luka_log", "Cannot write to directory: " + parent.getAbsolutePath());
            return;
        }

        final File file = new File(parent, fileName + ".png");
        if (file.exists()) {
            file.delete();
        }

        file.createNewFile();

//        writer = new BufferedWriter(new PrintWriter(new FileOutputStream(file)));
//
//        for (int i = 0; i < 10_000; i++) {
//            writer.write("fjdksafkdsajflkadsjklfjkalnsudvndluafnfuindsjkhfadksh");
//            writer.newLine();
//        }

        writePictureToDirectory(file);

        Log.d("luka_log", String.format("Stored file: %24s - tag: %10s - in: %s", fileName, tag, parent.getAbsolutePath()));
    }
}
