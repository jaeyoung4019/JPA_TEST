package toy.project.local_specialty.local_famous_goods.domain.converter;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Converter
public class AES256Convertor implements AttributeConverter<String , String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return AES256Encoding.encrypt(attribute);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return AES256Encoding.decrypt(dbData);
        } catch (Exception e) {
            return "";
        }
    }

    static class AES256Encoding {
        public static final String ALG = "AES/CBC/PKCS5Padding";
        private static final String KEY = "jaesoon_toy_project_82912";
        private static final String IV = KEY.substring(0, 16); // 16byte
        // 암호화
        private static String encrypt(String text) throws Exception {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(IV.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        }
        //복호화
        private static String decrypt(String cipherText) throws Exception {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(IV.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        }
    }

}

