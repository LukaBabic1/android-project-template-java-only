package template.android.com.device.crypto.engine;

import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.KeyStore;

import template.android.com.device.crypto.provider.SymmetricKeyProviderV23Impl;
import template.android.com.domain.crypto.engine.CryptoEngine;
import template.android.com.domain.crypto.engine.CryptoEngineFactory;
import template.android.com.domain.crypto.engine.CryptoEngineFactoryImpl;
import template.android.com.domain.crypto.provider.SymmetricKeyProvider;

@RunWith(AndroidJUnit4.class)
public final class AesCryptoEngineTestV23 {

    private SymmetricKeyProvider symmetricKeyProvider;
    private CryptoEngineFactory cryptoEngineFactory;

    private CryptoEngine cryptoEngine;

    @Before
    public void setUp() throws Exception {

        symmetricKeyProvider = new SymmetricKeyProviderV23Impl(KeyStore.getInstance("AndroidKeyStore"));
        cryptoEngineFactory = new CryptoEngineFactoryImpl(symmetricKeyProvider);

        symmetricKeyProvider.deleteSymmetricKey("alias");

        cryptoEngine = cryptoEngineFactory.createCryptoEngineForKeyAlias("alias");
    }

    @After
    public void tearDown() throws Exception {
        symmetricKeyProvider.deleteSymmetricKey("alias");
    }

    @Test
    public void testAesCryptoEngineEncryptingAndDecryptingUsingSymmetricKeyProvider() {

        // Given
        final byte[] plainText = "plain".getBytes();

        // When
        final byte[] cryptogram = cryptoEngine.encrypt(plainText);
        final byte[] decryptedCryptogram = cryptoEngine.decrypt(cryptogram);

        // Then
        Assert.assertArrayEquals(plainText, decryptedCryptogram);
    }
}
