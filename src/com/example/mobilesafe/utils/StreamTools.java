package com.example.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class StreamTools{

	public static String readFromStream(InputStream is)throws IOException{
		//输入流
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=is.read(buffer))!=-1){
			baos.write(buffer,0,len);
		}
		is.close();
		String result=baos.toString();
		baos.close();
		return result;
		
	}
	
}