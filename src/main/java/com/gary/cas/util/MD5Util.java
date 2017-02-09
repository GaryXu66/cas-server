package com.gary.cas.util;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	
	/**
	 * 加密
	 * @param data 原始数据
	 * @param key 加密key
	 * @param encode 编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String data, String key, Charset encode) {
		return DigestUtils.md5Hex(getContentBytes(data+key,encode));
	}
	
	private static byte[] getContentBytes(String content, Charset charset) {
        if (null == charset) {
            return content.getBytes();
        }
        return content.getBytes(charset);
    }
	
	public static void main(String[] args) {
		String result = MD5Util.encrypt("abcdefghijklmnopqrstuvwxyz", "123", Charset.forName("UTF-8"));
		String result2 = MD5Util.encrypt("abcdefghijklmnopqrstuvwxyz", "123", Charset.forName("UTF-8"));
		System.out.println(result.equals(result2));
	}
}
