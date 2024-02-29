package pavelbich.fit.bstu.carvitta;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption_SHA256 {
    public static String encrypt(String input) {
        try {
            // Создаем экземпляр объекта MessageDigest с использованием алгоритма SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Преобразуем входную строку в массив байтов
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Преобразуем хеш в строку в шестнадцатеричном формате
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Возвращаем полученную строку
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
