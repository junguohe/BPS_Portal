package com.erry.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

/*no salt is added, if salt is needed, need a column in the table to save the salt for each record, which will cause complexity 
 * when retrieving the record when encrypted fields are used in search criteria
 * */

public class EncryptDecryptUtil {

	private static String CIPHER_ALGORITHM_AES = "AES";
	
	private static String DEFAULT_KEY = "AESBESXYZDF"; //TODO get from the key file
    
	public static String encryptByAES(String data,String key) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        Key securekey = getSecretKey(key);  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);  
        
        byte[] encryptedData = cipher.doFinal(data.getBytes("utf-8"));  
        
        byte[] base64str = new Base64().encode(encryptedData);
        
        String encryptedText = new String(base64str); 
        return encryptedText;  
    }  
	public static String encryptByAES(String data) throws Exception {  
        return  encryptByAES(data,DEFAULT_KEY);  
    }   
      
    public static String detryptByAES(String base64str,String key) throws Exception{  
        SecureRandom sr = new SecureRandom();  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);  
        Key securekey = getSecretKey(key);  
        cipher.init(Cipher.DECRYPT_MODE, securekey,sr);  
        
        byte[] decodedBytes = new Base64().decode(base64str.getBytes("utf-8"));
 
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);  
       
        return new String(decryptedBytes);  
    }
    
	public static String detryptByAES(String base64str) throws Exception {  
        return  detryptByAES(base64str,DEFAULT_KEY);  
    } 
	
	public static Key getSecretKey(String key) throws Exception {
		SecretKey securekey = null;
		if (key == null) {
			key = "";
		}
		KeyGenerator keyGenerator = KeyGenerator.getInstance(CIPHER_ALGORITHM_AES);
		//AlgorithmParameterSpec spec;
		keyGenerator.init(128, new SecureRandom(key.getBytes("utf-8")));
		securekey = keyGenerator.generateKey();
		return securekey;
	}
	
    public static void main(String[] args)throws Exception{  
        String message = "需要加密password中ABCDE";  
        //String key = "";  
        String entryptedMsg = encryptByAES(message);  
//        System.out.println("encrypted message is below :");  
//        System.out.println(entryptedMsg);  
          
        String decryptedMsg = detryptByAES("Tv6Wd+fcdSuZDFpGdhBRFw==");
        System.out.println(decryptedMsg);
    }

}
