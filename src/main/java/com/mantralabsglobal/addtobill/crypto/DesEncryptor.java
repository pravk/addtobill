package com.mantralabsglobal.addtobill.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DesEncryptor {
	
	private static final String ALGORITHM = "AES";
	
	@Value("${app.charge.token.secret}")
    private String keyValue;

	private Key key;
    /**
     * @param args
     * @throws Exception
     */

    public String encrypt(String valueToEnc) throws Exception {

        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);

        System.out.println("valueToEnc.getBytes().length "+valueToEnc.getBytes().length);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        System.out.println("encValue length" + encValue.length);
        byte[] encryptedByteValue = new Base64().encode(encValue);
        String encryptedValue = new String(encryptedByteValue);
        System.out.println("encryptedValue " + encryptedValue);

        return encryptedValue;
    }

    public String decrypt(String encryptedValue) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);

        byte[] decordedValue = new Base64().decode(encryptedValue);
        
        byte[] decryptedValue = c.doFinal(decordedValue);
        
        return new String(decryptedValue);
    }

    private Key generateKey() throws Exception {
    	if(key == null)
    		key = new SecretKeySpec(keyValue.getBytes(), ALGORITHM);
        return key;
    }
}
