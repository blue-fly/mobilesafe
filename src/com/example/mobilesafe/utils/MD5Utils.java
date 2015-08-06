package com.example.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 算法步骤
 * 1.用每个byte去和11111111做与运算并且得到的是int类型的值
 * 2.把int类型转成16进制并返回String类型
 * 3.不满八个二进制就补全
 * @author Administrator
 *
 */
public class MD5Utils {
	
	public static String md5Password(String password){
		try {
			//信息摘要器
			MessageDigest digest=MessageDigest.getInstance("md5");
			byte[] result =digest.digest(password.getBytes());
			StringBuffer buffer=new StringBuffer();
			for(byte b:result){
				int number=b&0xff;
				//返回一个单字节的十六进制
				String str=Integer.toHexString(number);
				//不足两位的补0
				if(str.length()==1){
					buffer.append("0");
				}
				buffer.append(str);
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
