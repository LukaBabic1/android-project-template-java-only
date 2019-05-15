package template.android.com.device.crypto;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.MessageDigest;

import template.android.com.domain.crypto.obfuscator.StringObfuscator;
import template.android.com.domain.crypto.obfuscator.StringObfuscatorImpl;

@RunWith(AndroidJUnit4.class)
public final class StringObfuscatorTest {

    private StringObfuscator stringObfuscator;

    @Before
    public void setUp() throws Exception {
        stringObfuscator = new StringObfuscatorImpl(MessageDigest.getInstance("SHA-256"));
    }

    @Test
    public void name() {
        System.out.println(stringObfuscator.obfuscateStringWithSHA1("1234"));
    }
}
