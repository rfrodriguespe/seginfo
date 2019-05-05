package criptografia;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter; //Usada na conversão de array de bytes para uma String hexadecimal

/**
 *
 * @author Jefferson Bruno de Assis Lima
 *         José Cláudio de Figueiredo Filho
 *         Rodrigo Ferreira Rodrigues
 * 
 */
public class Sha256 {

    /**
     *
     * @param data
     * @return
     */
    public String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); //Transforma o array em um hexadeciomal
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
        }
        return result;
    }

    private String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}
