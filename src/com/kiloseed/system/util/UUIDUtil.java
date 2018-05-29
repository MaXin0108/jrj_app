package com.kiloseed.system.util;
import java.util.UUID;


public class UUIDUtil {
	/**
	 * 获得uuid
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	/**
	 * 获得一个0到9的随机数
	 * @return
	 */
	public static String getRandomNumber(){
		return ""+((int)(Math.random()*10));
	}
	/**
	 * 产生定长的随机数
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append(getRandomNumber());
		}
		return sb.toString();
	}
	public static void main(String args[]){
		for(int i=0;i<20;i++){
			System.out.println(getRandomNumber(6));
		}
	}
}
