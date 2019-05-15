package template.android.com.device.crypto.provider;

import androidx.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import template.android.com.domain.crypto.digest.MessageDigestFactory;
import template.android.com.domain.crypto.digest.MessageDigestFactoryImpl;
import template.android.com.domain.crypto.obfuscator.StringObfuscatorImpl;

public final class SymmetricKeyProviderStorageTest {

    private static final int AES_KEY_SIZE = 128;

    private SymmetricKeyProviderStorage symmetricKeyProviderStorage;

    @Before
    public void setUp() throws Exception {

        final MessageDigestFactory messageDigestFactory = new MessageDigestFactoryImpl();

        symmetricKeyProviderStorage = SymmetricKeyProviderStorageImpl.create(InstrumentationRegistry.getTargetContext(),
                                                                             new StringObfuscatorImpl(messageDigestFactory.getSha1MessageDigest()));

        symmetricKeyProviderStorage.deleteAllKeys();
    }

    @Test
    public void testSymmetricKeyShouldNotContainAnyAliasByDefault() {

        // Given
        final String alias = "alias";

        // When
        final boolean isKeyPresent = symmetricKeyProviderStorage.isKeySavedInStorage(alias);

        // Then
        Assert.assertFalse(isKeyPresent);
    }

    @Test
    public void tesSymmetricKeyShouldBePresentWhenSaved() {

        // Given
        final String alias = "alias_1";
        final byte[] key = new byte[AES_KEY_SIZE];
        Arrays.fill(key, (byte) 4);

        // When
        symmetricKeyProviderStorage.saveKeyToPersistentStorage(alias, key);

        // Then
        Assert.assertTrue(symmetricKeyProviderStorage.isKeySavedInStorage(alias));
    }

    @Test
    public void testRemovingSavedSymmetricKeyFromStorage() {

        // Given
        final String alias = "alias_2";
        final byte[] key = new byte[AES_KEY_SIZE];
        Arrays.fill(key, (byte) 4);

        // When
        symmetricKeyProviderStorage.saveKeyToPersistentStorage(alias, key);
        symmetricKeyProviderStorage.deleteKey(alias);

        // Then
        Assert.assertFalse(symmetricKeyProviderStorage.isKeySavedInStorage(alias));
    }
}
