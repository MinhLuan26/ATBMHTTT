package utils;

import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class Chukydientu {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNING_ALGORITHM = "SHA256withRSA";
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static String signData(String plainText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKeyFromString(privateKeyStr);
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(plainText.getBytes("UTF-8"));
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    public static boolean verifyData(String plainText, String signatureBase64, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKeyFromString(publicKeyStr);
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(plainText.getBytes("UTF-8"));
        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
        return signature.verify(signatureBytes);
    }

    public static String convertKeyToString(Key key, String headType) {
        String base64 = Base64.getEncoder().encodeToString(key.getEncoded());
        return "-----BEGIN " + headType + "-----\n" + insertNewlines(base64) + "\n-----END " + headType + "-----";
    }

    private static PrivateKey getPrivateKeyFromString(String keyStr) throws Exception {
        String clean = keyStr.replaceAll("-----BEGIN.*KEY-----", "").replaceAll("-----END.*KEY-----", "").replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(clean);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(spec);
    }

    private static PublicKey getPublicKeyFromString(String keyStr) throws Exception {
        String clean = keyStr.replaceAll("-----BEGIN.*KEY-----", "").replaceAll("-----END.*KEY-----", "").replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(clean);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(spec);
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
