package mx.edu.utez.mexprotec.config;

/*
* @Service
@Slf4j
public class TextDecrypt {
    @Value("${encryption.secret-key}")
    private String secretKey;

    public String decrypt(String data) {
        try {
            byte[] keyData = secretKey.getBytes();
            SecretKeySpec ks = new SecretKeySpec(keyData, "Blowfish");
            byte[] encryptedTextBytes = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, ks);
            byte[] decryptedBytes = cipher.doFinal(encryptedTextBytes);
            String decryptedString = new String(decryptedBytes, Charset.forName("UTF-8"));
            return decryptedString;
        } catch (Exception e) {
            log.error("Error during decryption: {}", e.getMessage());
            // Imprime la cadena codificada que causó el error para ayudar a la depuración
            log.error("Encoded string causing error: {}", data);
            return null;
        }
    }

    public String encrypt(String data) {
        try {
            byte[] KeyData = secretKey.getBytes();
            SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, KS);
            String encryptedtext = Base64.getEncoder().
                    encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
            return encryptedtext;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
* */
