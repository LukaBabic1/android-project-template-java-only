package template.android.com.device.crypto.engine;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.KeyStore;

import template.android.com.device.crypto.provider.SymmetricKeyProviderStorageImpl;
import template.android.com.device.crypto.provider.SymmetricKeyProviderV19Impl;
import template.android.com.device.time.CurrentTimeProviderImpl;
import template.android.com.domain.crypto.digest.MessageDigestFactoryImpl;
import template.android.com.domain.crypto.engine.CryptoEngine;
import template.android.com.domain.crypto.engine.CryptoEngineFactory;
import template.android.com.domain.crypto.engine.CryptoEngineFactoryImpl;
import template.android.com.domain.crypto.obfuscator.StringObfuscatorImpl;
import template.android.com.domain.crypto.provider.SymmetricKeyProvider;

@RunWith(AndroidJUnit4.class)
public final class AesCryptoEngineTestV19 {

    private CryptoEngineFactory cryptoEngineFactory;
    private SymmetricKeyProvider symmetricKeyProvider;

    private CryptoEngine aesCryptoEngine;

    @Before
    public void setUp() throws Exception {

        final Context context = InstrumentationRegistry.getTargetContext().getApplicationContext();

        symmetricKeyProvider = new SymmetricKeyProviderV19Impl(context,
                                                               new CurrentTimeProviderImpl(),
                                                               KeyStore.getInstance("AndroidKeyStore"),
                                                               SymmetricKeyProviderStorageImpl.create(context,
                                                                                                      new StringObfuscatorImpl(new MessageDigestFactoryImpl().getSha1MessageDigest())));

        cryptoEngineFactory = new CryptoEngineFactoryImpl(symmetricKeyProvider);

        symmetricKeyProvider.deleteSymmetricKey("alias");

        aesCryptoEngine = cryptoEngineFactory.createCryptoEngineForKeyAlias("alias");
    }

    @Test
    public void testAesCryptoEngineEncryptingAndDecryptingUsingSymmetricKeyProviderV19() {

        // Given
        final byte[] plainText = "plain".getBytes();

        // When
        final byte[] cryptogram = aesCryptoEngine.encrypt(plainText);
        final byte[] decryptedCryptogram = aesCryptoEngine.decrypt(cryptogram);

        // Then
        Assert.assertArrayEquals(plainText, decryptedCryptogram);
    }
}
