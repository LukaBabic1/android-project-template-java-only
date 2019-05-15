package template.android.com.device.crypto.provider;

import android.content.Context;
import androidx.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyStore;

import javax.crypto.SecretKey;

import template.android.com.device.time.CurrentTimeProviderImpl;
import template.android.com.domain.crypto.CommonCryptoConstants;
import template.android.com.domain.crypto.digest.MessageDigestFactory;
import template.android.com.domain.crypto.digest.MessageDigestFactoryImpl;
import template.android.com.domain.crypto.obfuscator.NoOpStringObfuscator;
import template.android.com.domain.crypto.provider.SymmetricKeyProvider;

public final class SymmetricKeyProviderV19Test {

    private Context context;

    private MessageDigestFactory messageDigestFactory;

    private SymmetricKeyProvider symmetricKeyProvider;

    @Before
    public void setUp() throws Exception {

        context = InstrumentationRegistry.getTargetContext().getApplicationContext();

        messageDigestFactory = new MessageDigestFactoryImpl();

        symmetricKeyProvider = new SymmetricKeyProviderV19Impl(context,
                                                               new CurrentTimeProviderImpl(),
                                                               KeyStore.getInstance(CommonCryptoConstants.ANDROID_KEY_STORE),
                                                               SymmetricKeyProviderStorageImpl.create(context,
                                                                                                      new NoOpStringObfuscator()));

        symmetricKeyProvider.deleteAllKeys();
    }

    @Test(timeout = 5000L)
    public void testGettingKeyFromProvider() {

        // Given
        final String alias = "alias_4";

        // When
        final SecretKey secretKey = symmetricKeyProvider.getSymmetricAesCbcPkcs7PaddingKeyForAlias(alias);

        // Then
        Assert.assertNotNull(secretKey);
    }

    @Test(timeout = 5000L)
    public void testProviderShouldReturnSameKeyEveryTime() {

        // Given
        final String alias = "alias_5";

        // When
        final SecretKey secretKey_1 = symmetricKeyProvider.getSymmetricAesCbcPkcs7PaddingKeyForAlias(alias);
        final SecretKey secretKey_2 = symmetricKeyProvider.getSymmetricAesCbcPkcs7PaddingKeyForAlias(alias);

        // Then
        Assert.assertEquals(secretKey_1, secretKey_2);
    }
}
