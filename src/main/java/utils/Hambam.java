package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Security;

public class Hambam {
    public static final String[] ALGORITHMS = {
        "SHA-256", "SHA-512", "MD5", "SHA-1", 
        "SHA-224", "SHA-384", "SHA-512/224", "SHA-512/256",
        "SHA3-224", "SHA3-256", "SHA3-384", "SHA3-512", "MD2",
        "BLAKE2b-512", "BLAKE2s-256"
    };

    static {
        try {
            Class<?> bcClass = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
            java.security.Provider bcProvider = (java.security.Provider) bcClass.getDeclaredConstructor().newInstance();
            Security.addProvider(bcProvider);
            System.out.println("[Gợi ý] Đã kích hoạt thành công thư viện ngoài hỗ trợ BLAKE2b và BLAKE2s!");
        } catch (Exception e) {
            System.out.println("[Lưu ý] Chưa tìm thấy thư viện BouncyCastle. Các thuật toán BLAKE2 sẽ tạm thời chưa dùng được.");
        }
    }

    public static String hashText(String input, String algorithm) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
        return bytesToHex(hashBytes);
    }

    public static String hashFile(File file, String algorithm) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        try (InputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}