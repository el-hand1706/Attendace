package utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CipherManager {
	
	private static String csrfToken = "";
	private static final String KEY = "YKo83n14SWf7o8G5";
    private static final String ALGORITHM = "AES";
    private static int TOKEN_LENGTH = 16;//16*2=32バイト
    
    public void setCsrfToken(String token){
    	
    }
    /**
     * セキュリティトークン作成
     * @return
     */
    public static void getCsrfToken() {
        byte token[] = new byte[TOKEN_LENGTH];
        StringBuffer buf = new StringBuffer();
        SecureRandom random = null;
     
        try {
        	random = SecureRandom.getInstance("SHA1PRNG");
        	random.nextBytes(token);
     
        	for (int i = 0; i < token.length; i++) {
        		buf.append(String.format("%02x", token[i]));
        	}
     
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
        csrfToken = buf.toString();
        System.out.println(csrfToken);
    }
    
    /**
     * 暗号化
     * @param source
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
	 public static String encrypt(String source) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		 Cipher cipher = Cipher.getInstance(ALGORITHM);
	     cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), ALGORITHM));
	     return new String(Base64.getEncoder().encode(cipher.doFinal(source.getBytes())));
	 }
	 
	 /**
	  * 複合化
	  * @param encryptSource
	  * @return
	  * @throws NoSuchAlgorithmException
	  * @throws NoSuchPaddingException
	  * @throws InvalidKeyException
	  * @throws IllegalBlockSizeException
	  * @throws BadPaddingException
	  */
	 public static String decrypt(String encryptSource) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		 Cipher cipher = Cipher.getInstance(ALGORITHM);
	     cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(), ALGORITHM));
	     return new String(cipher.doFinal(Base64.getDecoder().decode(encryptSource.getBytes())));
	 }
	    
	    
}
