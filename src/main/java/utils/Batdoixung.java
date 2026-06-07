package utils;

import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class Batdoixung {
    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
    
    public static String convertPublicKeyToString(PublicKey publicKey) {
        String base64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        return "-----BEGIN PUBLIC KEY-----\n" + insertNewlines(base64) + "\n-----END PUBLIC KEY-----";
    }
    
    public static String convertPrivateKeyToString(PrivateKey privateKey) {
        String base64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        return "-----BEGIN PRIVATE KEY-----\n" + insertNewlines(base64) + "\n-----END PRIVATE KEY-----";
    }
    
    public static PublicKey getPublicKeyFromString(String keyStr) throws Exception {
        String cleanKey = cleanKeyString(keyStr);
        byte[] keyBytes = Base64.getDecoder().decode(cleanKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePublic(spec);
    }

    public static PrivateKey getPrivateKeyFromString(String keyStr) throws Exception {
        String cleanKey = cleanKeyString(keyStr);
        byte[] keyBytes = Base64.getDecoder().decode(cleanKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePrivate(spec);
    }

    public static String encrypt(String plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKeyFromString(publicKeyStr);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String cipherText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKeyFromString(privateKeyStr);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decryptedBytes, "UTF-8");
    }

    private static String cleanKeyString(String keyStr) {
        return keyStr.replaceAll("-----BEGIN.*KEY-----", "")
                     .replaceAll("-----END.*KEY-----", "")
                     .replaceAll("\\s", "");
    }

    private static String insertNewlines(String input) {
        StringBuilder sb = new StringBuilder(input);
        int i = 64;
        while (i < sb.length()) {
            sb.insert(i, "\n");
            i += 65;
        }
        return sb.toString();
    }
}
