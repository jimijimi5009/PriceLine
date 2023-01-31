package utility;

import org.apache.commons.codec.binary.Base64;

public class DecryptEncripted {



    public static String decryptedPassword(String passWord){

        byte[] decodedString = new byte[0];
        try {
            decodedString = Base64.decodeBase64(passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(new String(decodedString));
    }

}
