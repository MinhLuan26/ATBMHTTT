package utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Doixung {
    public static String encryptAffine(String text, int a, int b) {
        if (modInverse(a, 26) == -1) throw new IllegalArgumentException("Hệ số 'a' phải nguyên tố cùng nhau với 26!");
        StringBuilder sb = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                int x = c - 'A';
                int enc = (a * x + b) % 26;
                sb.append((char) (enc + 'A'));
            } else sb.append(c);
        }
        return sb.toString();
    }

    public static String decryptAffine(String text, int a, int b) {
        int aInv = modInverse(a, 26);
        if (aInv == -1) throw new IllegalArgumentException("Hệ số 'a' phải nguyên tố cùng nhau with 26!");
        StringBuilder sb = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                int y = c - 'A';
                int dec = (aInv * (y - b)) % 26;
                if (dec < 0) dec += 26;
                sb.append((char) (dec + 'A'));
            } else sb.append(c);
        }
        return sb.toString();
    }

    public static String encryptHill2x2(String text, int[] keyMat) {
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "");
        if (cleanText.length() % 2 != 0) cleanText += "X";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cleanText.length(); i += 2) {
            int p1 = cleanText.charAt(i) - 'A';
            int p2 = cleanText.charAt(i + 1) - 'A';
            int c1 = (keyMat[0] * p1 + keyMat[1] * p2) % 26;
            int c2 = (keyMat[2] * p1 + keyMat[3] * p2) % 26;
            sb.append((char) (c1 + 'A')).append((char) (c2 + 'A'));
        }
        return sb.toString();
    }

    public static String decryptHill2x2(String text, int[] keyMat) {
        int det = (keyMat[0] * keyMat[3] - keyMat[1] * keyMat[2]) % 26;
        if (det < 0) det += 26;
        int detInv = modInverse(det, 26);
        if (detInv == -1) throw new IllegalArgumentException("Ma trận khóa không khả nghịch (Determinant không nguyên tố cùng nhau với 26)!");
        int[] invMat = {
            (keyMat[3] * detInv) % 26,
            ((-keyMat[1]) * detInv) % 26,
            ((-keyMat[2]) * detInv) % 26,
            (keyMat[0] * detInv) % 26
        };
        for(int i=0; i<4; i++) if(invMat[i] < 0) invMat[i] += 26;
        StringBuilder sb = new StringBuilder();
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "");
        for (int i = 0; i < cleanText.length(); i += 2) {
            int c1 = cleanText.charAt(i) - 'A';
            int c2 = cleanText.charAt(i + 1) - 'A';
            int p1 = (invMat[0] * c1 + invMat[1] * c2) % 26;
            int p2 = (invMat[2] * c1 + invMat[3] * c2) % 26;
            sb.append((char) (p1 + 'A')).append((char) (p2 + 'A'));
        }
        return sb.toString();
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) if ((a * x) % m == 1) return x;
        return -1;
    }

    public static String encryptCaesar(String text, int key) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                sb.append((char) ((c - base + key) % 26 + base));
            } else sb.append(c);
        }
        return sb.toString();
    }
    
    public static void processFile(int cipherMode, File inFile, File outFile, 
                                   String algorithm, String modeOfOp, String paddingScheme, 
                                   byte[] keyBytes, byte[] ivBytes) throws Exception {
        String transformation = algorithm + "/" + modeOfOp + "/" + paddingScheme;
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, algorithm);
        if (modeOfOp.equalsIgnoreCase("ECB")) {
            cipher.init(cipherMode, secretKey);
        } else {
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(cipherMode, secretKey, ivSpec);
        }
        try (FileInputStream fis = new FileInputStream(inFile);
             FileOutputStream fos = new FileOutputStream(outFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) fos.write(output);
            }
            byte[] outputBytesFinal = cipher.doFinal();
            if (outputBytesFinal != null) fos.write(outputBytesFinal);
        }
    }
    
    public static String encryptSubstitution(String text, String mapKey) {
        if (mapKey.length() != 26) throw new IllegalArgumentException("Khóa thay thế phải chứa đúng 26 ký tự chuỗi!");
        mapKey = mapKey.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                boolean isUpper = Character.isUpperCase(c);
                int idx = Character.toUpperCase(c) - 'A';
                char enc = mapKey.charAt(idx);
                sb.append(isUpper ? enc : Character.toLowerCase(enc));
            } else sb.append(c);
        }
        return sb.toString();
    }

    public static String decryptSubstitution(String text, String mapKey) {
        if (mapKey.length() != 26) throw new IllegalArgumentException("Khóa thay thế phải chứa đúng 26 ký tự chuỗi!");
        mapKey = mapKey.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                boolean isUpper = Character.isUpperCase(c);
                char searchChar = Character.toUpperCase(c);
                int idx = mapKey.indexOf(searchChar);
                if (idx == -1) { sb.append(c); continue; }
                char dec = (char) ('A' + idx);
                sb.append(isUpper ? dec : Character.toLowerCase(dec));
            } else sb.append(c);
        }
        return sb.toString();
    }
    
    public static String encryptVigenere(String text, String key) {
        StringBuilder sb = new StringBuilder();
        key = key.toUpperCase();
        int keyIdx = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int k = key.charAt(keyIdx % key.length()) - 'A';
                sb.append((char) ((c - base + k) % 26 + base));
                keyIdx++;
            } else sb.append(c);
        }
        return sb.toString();
    }

    public static String decryptVigenere(String text, String key) {
        StringBuilder sb = new StringBuilder();
        key = key.toUpperCase();
        int keyIdx = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int k = key.charAt(keyIdx % key.length()) - 'A';
                int dec = (c - base - k) % 26;
                if (dec < 0) dec += 26;
                sb.append((char) (dec + base));
                keyIdx++;
            } else sb.append(c);
        }
        return sb.toString();
    }

    public static String encryptPermutation(String text, int[] key) {
        String clean = text.toUpperCase().replaceAll("[^A-Z]", "");
        int m = key.length;
        while (clean.length() % m != 0) clean += "X";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clean.length(); i += m) {
            char[] block = new char[m];
            for (int j = 0; j < m; j++) {
                block[key[j]] = clean.charAt(i + j);
            }
            sb.append(block);
        }
        return sb.toString();
    }

    public static String decryptPermutation(String text, int[] key) {
        String clean = text.toUpperCase().replaceAll("[^A-Z]", "");
        int m = key.length;
        if (clean.length() % m != 0) throw new IllegalArgumentException("Độ dài văn bản hoán vị không hợp lệ!");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clean.length(); i += m) {
            char[] block = new char[m];
            for (int j = 0; j < m; j++) {
                block[j] = clean.charAt(i + key[j]);
            }
            sb.append(block);
        }
        return sb.toString();
    }
}
