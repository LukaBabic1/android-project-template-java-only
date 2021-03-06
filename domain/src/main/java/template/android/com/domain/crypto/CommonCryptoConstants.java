package template.android.com.domain.crypto;

public final class CommonCryptoConstants {

    public static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    public static final String ALGORITHM_AES = "AES";
    public static final String ALGORITHM_RSA = "RSA";

    public static final int AES_128_KEY_SIZE_BYTES = 1 << 4;
    public static final int AES_256_KEY_SIZE_BYTES = 1 << 5;
    public static final int RSA_2048_KEY_SIZE_BITS = 1 << 11;

    private CommonCryptoConstants() {
        throw new AssertionError();
    }
}
