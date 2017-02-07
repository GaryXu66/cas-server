package com.gary.cas.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	/**
	 * 加密
	 * @param data 原始数据
	 * @param key 加密key
	 * @param encode 编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encrypt(String data, String key, Charset encode) throws NoSuchAlgorithmException{
		return getMD5Digest().digest((data+key).getBytes(encode));
	}
	
	private static MessageDigest getMD5Digest() throws NoSuchAlgorithmException{
		return MessageDigest.getInstance("MD5");
	}

	public static void main(String[] args) {
		try {
			byte[] result = MD5Util.encrypt("abcdefghijklmnopqrstuvwxyz", "123", Charset.forName("UTF-8"));
			System.out.println(String.valueOf(result));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
