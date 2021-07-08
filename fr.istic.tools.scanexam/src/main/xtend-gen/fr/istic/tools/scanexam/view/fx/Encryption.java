package fr.istic.tools.scanexam.view.fx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Classe pour encrypter des données
 * Source : https://www.delftstack.com/fr/howto/java/java-password-encryption/
 */
@SuppressWarnings("all")
public class Encryption {
  public static SecretKeySpec createSecretKey(final char[] password, final byte[] salt, final int iterationCount, final int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
    PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
    SecretKey keyTmp = keyFactory.generateSecret(keySpec);
    byte[] _encoded = keyTmp.getEncoded();
    return new SecretKeySpec(_encoded, "AES");
  }
  
  public static String encrypt(final String dataToEncrypt, final SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
    Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    pbeCipher.init(Cipher.ENCRYPT_MODE, key);
    AlgorithmParameters parameters = pbeCipher.getParameters();
    IvParameterSpec ivParameterSpec = parameters.<IvParameterSpec>getParameterSpec(IvParameterSpec.class);
    byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
    byte[] iv = ivParameterSpec.getIV();
    StringConcatenation _builder = new StringConcatenation();
    String _base64Encode = Encryption.base64Encode(iv);
    _builder.append(_base64Encode);
    _builder.append(":");
    String _base64Encode_1 = Encryption.base64Encode(cryptoText);
    _builder.append(_base64Encode_1);
    return _builder.toString();
  }
  
  private static String base64Encode(final byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }
  
  public static String decrypt(final String string, final SecretKeySpec key) throws GeneralSecurityException, IOException {
    String iv = string.split(":")[0];
    String property = string.split(":")[1];
    Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    byte[] _base64Decode = Encryption.base64Decode(iv);
    IvParameterSpec _ivParameterSpec = new IvParameterSpec(_base64Decode);
    pbeCipher.init(Cipher.DECRYPT_MODE, key, _ivParameterSpec);
    byte[] _doFinal = pbeCipher.doFinal(Encryption.base64Decode(property));
    return new String(_doFinal, "UTF-8");
  }
  
  private static byte[] base64Decode(final String property) throws IOException {
    return Base64.getDecoder().decode(property);
  }
}
