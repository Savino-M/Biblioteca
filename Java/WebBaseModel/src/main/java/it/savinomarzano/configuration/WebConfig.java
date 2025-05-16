package it.savinomarzano.configuration;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private DecryptionInterceptor decryptionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(decryptionInterceptor).addPathPatterns("/**"); // Applica a tutti gli endpoint
    }

}

@Component
class DecryptionInterceptor implements HandlerInterceptor {

    @Value("${encryption.key}")
    private String key;

    private static final String ALGORITHM = "AES";

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private String decrypt(String encryptedData) throws Exception {

        // Converti il formato Base64 URL-safe in Base64 standard
        String base64Standard = encryptedData
                .replace('-', '+') // Converti '-' in '+'
                .replace('_', '/') // Converti '_' in '/'
                .concat("==".substring(0, (4 - encryptedData.length() % 4) % 4)); // Aggiungi padding '=' se mancante

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        // Decodifica il Base64
        byte[] encryptedDataWithIv = Base64.getDecoder().decode(base64Standard);

        // Estrai l'IV
        byte[] iv = new byte[16];
        System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);

        // Estrai i dati cifrati
        byte[] encryptedDataBytes = new byte[encryptedDataWithIv.length - iv.length];
        System.arraycopy(encryptedDataWithIv, iv.length, encryptedDataBytes, 0, encryptedDataBytes.length);

        // Inizializza il Cipher per la decrittazione
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hexStringToByteArray(key), ALGORITHM), ivParameterSpec);

        // Decifra i dati
        byte[] decryptedData = cipher.doFinal(encryptedDataBytes);
        return new String(decryptedData, "UTF-8");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        List<String> parametri = Arrays.asList("idElemento", "idUtente", "idPrenotazione", "email", "password");

        // Decrittazione di PathVariables
        @SuppressWarnings("unchecked")
        Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables != null) {
            Map<String, String> newPathVariables = new HashMap<>(pathVariables);
            for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
                if (parametri.contains(entry.getKey())) {
                    String encryptedValue = entry.getValue();
                    String decryptedValue = decrypt(encryptedValue);
                    // Sovrascrive il valore cifrato con quello decifrato
                    newPathVariables.put(entry.getKey(), decryptedValue);
                }
            }
            request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, newPathVariables);
        }

        return true;
    }

    // Metodo per convertire una stringa esadecimale in byte array
    private byte[] hexStringToByteArray(String s) {

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;

    }

}
