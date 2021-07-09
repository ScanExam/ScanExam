package fr.istic.tools.scanexam.utils

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.AlgorithmParameters
import java.security.GeneralSecurityException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.Base64

/** 
 * Classe pour encrypter des données
 * Source : https://www.delftstack.com/fr/howto/java/java-password-encryption/
 */
class Encryption {
	def static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount,
		int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
		var SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
		var PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength)
		var SecretKey keyTmp = keyFactory.generateSecret(keySpec)
		return new SecretKeySpec(keyTmp.getEncoded(), "AES")
	}

	def static String encrypt(String dataToEncrypt,
		SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
		var Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
		pbeCipher.init(Cipher.ENCRYPT_MODE, key)
		var AlgorithmParameters parameters = pbeCipher.getParameters()
		var IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec)
		var byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"))
		var byte[] iv = ivParameterSpec.getIV()
		return '''«base64Encode(iv)»:«base64Encode(cryptoText)»'''
	}

	def private static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes)
	}

	def static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
		var String iv = string.split(":").get(0)
		var String property = string.split(":").get(1)
		var Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)))
		return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8")
	}

	def private static byte[] base64Decode(String property) throws IOException {
		return Base64.getDecoder().decode(property)
	}
}
