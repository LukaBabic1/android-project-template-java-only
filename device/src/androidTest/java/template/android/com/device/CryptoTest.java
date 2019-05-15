package template.android.com.device;

import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import android.util.Log;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.x500.X500Principal;

import template.android.com.device.crypto.keystore.SecureKeystoreImpl;
import template.android.com.domain.crypto.keystore.SecureKeystore;
import template.android.com.domain.utils.byteformat.HEX;

@RunWith(AndroidJUnit4.class)
public final class CryptoTest {

    private SecureKeystore secureKeystore;

    @Before
    public void setUp() throws Exception {
        secureKeystore = new SecureKeystoreImpl(KeyStore.getInstance("AndroidKeystore"));
        secureKeystore.removeAlias("alias");
    }

    @Test
    public void name() {
        Assert.assertThat(secureKeystore.containsAlias("fdjasklfd"), CoreMatchers.is(false));
    }

    @Test
    public void testCreatingSymmetricKey() throws Exception {

        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        keyGenerator.init(new KeyGenParameterSpec.Builder("alias", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                  .setKeySize(128)
                                  .build());

        Assert.assertFalse(secureKeystore.containsAlias("alias"));

        final SecretKey secretKey = keyGenerator.generateKey();

        System.out.println("Key: " + Arrays.toString(secretKey.getEncoded()));

        Assert.assertTrue(secureKeystore.containsAlias("alias"));
    }

    @Test(timeout = 5000L)
    public void testCreatingKeyPair() throws Exception {

        System.out.println();

        final String alias = "luka_alias";

        final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (keyStore.containsAlias(alias)) {
            keyStore.deleteEntry(alias);
        }

        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

        final KeyPairGeneratorSpec keyPairGeneratorSpec = new KeyPairGeneratorSpec.Builder(InstrumentationRegistry.getTargetContext())
                .setAlias(alias)
                .setSubject(new X500Principal("CN=" + alias))
                .setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()))
                .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(2048, BigInteger.TEN))
                .setStartDate(new Date(System.currentTimeMillis()))
                .setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10_000)))
                .build();

        keyPairGenerator.initialize(keyPairGeneratorSpec);

        final KeyPair keyPair = keyPairGenerator.generateKeyPair();

        System.out.println("Keystore contains alias: " + keyStore.containsAlias(alias));
    }

    @Test
    public void safdsfkljdsalk() throws Exception {

        final String alias = "aliasdsdfasd";

        final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        keyStore.containsAlias(alias);

        if (keyStore.containsAlias(alias)) {
            keyStore.deleteEntry(alias);
        }

        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        final KeyPairGeneratorSpec keyPairGeneratorSpec = new KeyPairGeneratorSpec.Builder(InstrumentationRegistry.getTargetContext())
                .setAlias(alias)
                .setSubject(new X500Principal("CN=" + alias))
                .setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()))
                .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F0))
                .setStartDate(new Date(System.currentTimeMillis()))
                .setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10)))
                .build();

//        keyPairGenerator.initialize(keyPairGeneratorSpec);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();

        System.out.println();
    }

    @Test
    public void testPubPrivateKeyGen() throws Exception {

        final String alias = "alias";

        final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (keyStore.containsAlias(alias)) {
            keyStore.deleteEntry(alias);
        }

        final KeyPairGeneratorSpec keyPairGeneratorSpec = new KeyPairGeneratorSpec.Builder(InstrumentationRegistry.getTargetContext())
                .setAlias(alias)
                .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F0))
                .setSubject(new X500Principal("CN=" + alias))
                .setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()))
                .setStartDate(new Date(System.currentTimeMillis()))
                .setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(100)))
                .build();

        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        keyPairGenerator.initialize(keyPairGeneratorSpec);

        final KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String toCrypt = "abcd";

        final Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(KeyProperties.PURPOSE_ENCRYPT, keyPair.getPrivate());

        final byte[] bytes = encryptCipher.doFinal(toCrypt.getBytes());

        System.out.println("Encrypted: " + HEX.toHEX(bytes));

        final Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptCipher.init(KeyProperties.PURPOSE_DECRYPT, keyPair.getPublic());

        final String decrypted = new String(decryptCipher.doFinal(bytes));

        System.out.println("Decrypted: " + decrypted);

        System.out.println("Does keystore contain alias: " + keyStore.containsAlias(alias));

        final KeyStore.PrivateKeyEntry key = KeyStore.PrivateKeyEntry.class.cast(keyStore.getEntry(alias, null));
    }

    @Test
    public void testGettingFromKeystore() throws Exception {

        final String alias = "alias";

        final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");

        keyStore.load(null);

        System.out.println("Does keystore contain alias: " + keyStore.containsAlias(alias));

        final KeyStore.Entry key = keyStore.getEntry(alias, null);

//        final Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        encryptCipher.init(KeyProperties.PURPOSE_ENCRYPT, keyPair.getPrivate());
//
//        final byte[] bytes = encryptCipher.doFinal(toCrypt.getBytes());
//
//        System.out.println("Encrypted: " + HEX.toHEX(bytes));
//
//        final Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        decryptCipher.init(KeyProperties.PURPOSE_DECRYPT, keyPair.getPublic());
//
//        final String decrypted = new String(decryptCipher.doFinal(bytes));
//
//        System.out.println("Decrypted: " + decrypted);
    }

    @Test
    public void fjdsklsfaj() throws Exception {

        final String alias = "aliasdsdfasd";

        final KeyPairGeneratorSpec keyPairGeneratorSpec = new KeyPairGeneratorSpec.Builder(InstrumentationRegistry.getTargetContext())
                .setAlias(alias)
                .setSubject(new X500Principal("CN=" + alias))
                .setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()))
                .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F0))
                .setStartDate(new Date(System.currentTimeMillis()))
                .setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10_000)))
                .build();
    }

    @Test
    public void sampleTest() throws Exception {

        final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        final String alias = "alias";

        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                  .setKeySize(128)
                                  .build());

        keyGenerator.generateKey();

        System.out.println("Has key: " + keyStore.containsAlias(alias));
        keyStore.deleteEntry(alias);

        System.out.println("Has key: " + keyStore.containsAlias(alias));
    }

    @Test
    public void testSavingMultipleDataWithSameIV() throws Exception {

        final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        final KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

        keyGenerator.init(new KeyGenParameterSpec.Builder("alias", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                         .setRandomizedEncryptionRequired(false)
                         .setBlockModes(KeyProperties.BLOCK_MODE_CBC, KeyProperties.BLOCK_MODE_GCM, KeyProperties.BLOCK_MODE_CTR)
                         .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                         .build());

        final SecretKey secretKey = keyGenerator.generateKey();

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        final Random random = new Random();

        final byte[] bytes = new byte[128];
        final byte[] initVector = new byte[16];

        for (int i = 0; i < 10; i++) {

            random.nextBytes(bytes);
            random.nextBytes(initVector);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(initVector));

            Log.d("luka_log", HEX.toHEX(cipher.doFinal(bytes)));
        }
    }

    @Test
    public void testSecretKeyFactory() throws Exception {

    }
}
