package com.vwfsag.fso.service;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author qngl
 *
 */
@Service
public class EncryptServiceImpl implements EncryptService {

	private static final Logger log = LoggerFactory.getLogger(EncryptServiceImpl.class);

	private static final String ENCRYPT_FEED = "!@#$%QAZWSX142857";

	public static final String RSA = "RSA";
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	private static final String AES = "AES";
	private static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";

	@Override
	public String md5(String source) {
		return Base64.encodeBase64String(DigestUtils.md5(ENCRYPT_FEED + source));
	}

	@Override
	public String sha1(String source) {
		return Base64.encodeBase64String(DigestUtils.sha1(ENCRYPT_FEED + source));
	}

	public String aesEncrypt(String input, String key) {
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), AES);
			Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			log.error("Trying to encrypt via AES failed.", e);
		}
		return new String(Base64.encodeBase64(crypted));
	}

	public String aesDecrypt(String input, String key) {
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), AES);
			Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input));
		} catch (Exception e) {
			log.error("Trying to decrypt via AES failed.", e);
		}
		return new String(output);
	}

	public String decryptByPrivateKey(byte[] encryptedData, String privateKey) {
		try {
			byte[] keyBytes = Base64.decodeBase64(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return Base64.encodeBase64String(decryptedData);
		} catch (Exception e) {
			throw new RuntimeException("Trying to decryptByPrivateKey failed.", e);
		}
	}

	public String decryptByPublicKey(byte[] encryptedData, String publicKey) {
		try {
			byte[] keyBytes = Base64.decodeBase64(publicKey);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return Base64.encodeBase64String(decryptedData);
		} catch (Exception e) {
			throw new RuntimeException("Trying to decryptByPublicKey failed.", e);
		}
	}

	public String encryptByPublicKey(byte[] data, String publicKey) {
		try {
			byte[] keyBytes = Base64.decodeBase64(publicKey);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64.encodeBase64String(encryptedData);
		} catch (Exception e) {
			throw new RuntimeException("Trying to encryptByPublicKey failed.", e);
		}
	}

	public String encryptByPrivateKey(byte[] data, String privateKey) {
		try {
			byte[] keyBytes = Base64.decodeBase64(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64.encodeBase64String(encryptedData);
		} catch (Exception e) {
			throw new RuntimeException("Trying to encryptByPrivateKey failed.", e);
		}
	}

	public Map<String, String> genKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			Map<String, String> keyMap = new HashMap<String, String>(2);
			keyMap.put(PUBLIC_KEY, Base64.encodeBase64String(publicKey.getEncoded()));
			keyMap.put(PRIVATE_KEY, Base64.encodeBase64String(privateKey.getEncoded()));
			return keyMap;
		} catch (Exception e) {
			throw new RuntimeException("Trying to encryptByPrivateKey failed.", e);
		}
	}

}
