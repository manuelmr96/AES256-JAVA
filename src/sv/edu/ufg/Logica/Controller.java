
package sv.edu.ufg.Logica;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import sv.edu.ufg.Forms.Principal;

/**
 *
 * @author Manuel Moran
 */
public class Controller {

    private static String llaveSecreta = "WgG6dcDBsHzpc6A";
    private static String salt = "uhTP682F3vyxHaB";

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible( true );
    }
    
    //AES256 

    public static String encripta( String stringAEncriptar ){
        try{
            // Decrlare Array of bytes
            byte[] iv = { 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0 };

            IvParameterSpec ivspec = new IvParameterSpec( iv ); 
            SecretKeyFactory factory = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" );
            KeySpec spec = new PBEKeySpec( llaveSecreta.toCharArray(), salt.getBytes(), 65536, 256 );
            SecretKey tmp = factory.generateSecret( spec );
            SecretKeySpec secretKey = new SecretKeySpec( tmp.getEncoded(), "AES" );
            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );

            
            cipher.init( Cipher.ENCRYPT_MODE, secretKey, ivspec );
            
            return Base64.getEncoder().encodeToString( cipher.doFinal( stringAEncriptar.getBytes( "UTF-8" ) ) );

        }catch ( Exception e ){
            System.out.println( "Error Mientras se Encriptaba : " + e.toString() );
        }

        return null;
    }
    
    public static String desencripta( String stringADesencriptar ) {
        try{
            byte[] iv = { 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0 };

            IvParameterSpec ivspec = new IvParameterSpec( iv );
            SecretKeyFactory factory = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" );
            KeySpec spec = new PBEKeySpec( llaveSecreta.toCharArray(), salt.getBytes(), 65536, 256 );
            SecretKey tmp = factory.generateSecret( spec );
            SecretKeySpec secretKey = new SecretKeySpec( tmp.getEncoded(), "AES" );
            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5PADDING" );

            cipher.init( Cipher.DECRYPT_MODE, secretKey, ivspec );

            return new String( cipher.doFinal( Base64.getDecoder().decode( stringADesencriptar ) ) );

        }catch ( Exception e ) {
            System.out.println( "Error Mientras se Desencriptaba: " + e.toString() );
        }

        return null;
    }
    
}
