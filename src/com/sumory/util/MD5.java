package com.sumory.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class MD5 {
	public final static String MD5(String s) {
		if(s==null){
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("\\%aaa999","utf-8"));
		System.out.println(MD5("A28C385CC3A3E850CE38125DD178B45C"));
		System.out.println(MD5("method=buymerId=WQH0000000002order=100000160amt=0.1cur=RMBpName=url=http://59.60.30.170:80/mall/pay/ordermp=autoSplit=0lftName=zhishansysTime=20130806171943user_ip=192.168.1.1key=A28C385CC3A3E850CE38125DD178B45C").toUpperCase());
		System.out.println(MD5(("order=100000300status=1key=32BA357A8E0AC2E5267080B16E1C4F4A")));
	}
}