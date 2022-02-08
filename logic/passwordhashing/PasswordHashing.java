package by.epamtc.stanislavmelnikov.logic.passwordhashing;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordHashing {
    private static final String encryptionAlgorithm = "PBKDF2WithHmacSHA512";
    private static final int iterations = 10000;
    private static final int keyLength = 300;
    private static final String delimiter = " ";

    public static String hashPassword(String password) {
        String salt = generateRandom();
        String hashedString = getHashedString(salt, password);
        String passwordWithSalt = salt + delimiter + hashedString;
        return passwordWithSalt;
    }

    public static String hashPassword(String passwordToHash, String hashedPassword) {
        String salt = hashedPassword.split(delimiter)[0];
        String hashedString = getHashedString(salt, passwordToHash);
        String passwordWithSalt = salt + delimiter + hashedString;
        return passwordWithSalt;
    }

    public static String getHashedString(String salt, String passwordToHash) {
        char[] passwordChars = passwordToHash.toCharArray();
        byte[] saltBytes = salt.getBytes();
        byte[] hashedBytes = doHash(passwordChars, saltBytes, iterations, keyLength);
        return Hex.encodeHexString(hashedBytes);
    }


    public static byte[] doHash(final char[] password, final byte[] salt, final int iterations, final int keyLength) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(encryptionAlgorithm);
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandom() {
        long random = (long) (8999999999999999L * Math.random() + 1000000000000000L);
        return String.valueOf(random);
    }
}
