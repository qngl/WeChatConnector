package com.vwfsag.fso.service;

/**
 * @author liqiang
 *
 */
public interface EncryptService {

	String md5(String source);

	String sha1(String source);

	String aesEncrypt(String input, String key);

	String aesDecrypt(String input, String key);

}
