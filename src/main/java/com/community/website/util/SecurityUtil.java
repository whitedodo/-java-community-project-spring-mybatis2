/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): SecurityUtil.java
 * 비고(Description):
 * 
 */

package com.community.website.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	
	private final static String firstKey = "ha";
	private final static String lastKey = "ma";
	
	private final static String extFirstKey = "che";
	private final static String extLastKey = "rok";
	
	private final static String salt = "dodo";
    
	public static String getSaltKey() {
		return salt;
	}
	
	public static String generateMD5(String message) {
		return hashString(message, "MD5","UTF-8");
	}

	public static String generateSHA256(String message)  {
		return hashString(message, "SHA-256","UTF-8");
	}

	public static String generateSHA512(String message)  {
		return hashString(message, "SHA-512","UTF-8" );
	}
	
	/*
	 * 	암호키 치환으로 만들기
	 */
	public static String generateCustomKey(String message) {
		
		String key = generateMD5(message);
		StringBuilder builder = new StringBuilder(key);
		
		builder.setCharAt(0, firstKey.charAt(0));
		builder.setCharAt(1, firstKey.charAt(1));
		
		builder.setCharAt(key.length() - 2, lastKey.charAt( lastKey.length() - 2 ) );
		builder.setCharAt(key.length() - 1, lastKey.charAt( lastKey.length() -1 ) );
		
		return builder.toString();
		
	}

	/*
	 * 	암호키 치환으로 만들기
	 */
	public static String generateCustomExtKey(String message) {
		
		String key = generateMD5(message);
		StringBuilder builder = new StringBuilder(key);
		
		builder.setCharAt(0, extFirstKey.charAt(0));
		
		// System.out.println("세:" + String.valueOf( getRandom(1, 5)).charAt(0));
		char tmp = String.valueOf( getRandom(1, 5)).charAt(0);
		builder.setCharAt(1, getCharSecuritiesCode(Character.getNumericValue(tmp)) );
		
		tmp = String.valueOf( getRandom(1, 5)).charAt(0);
		builder.setCharAt(2, getCharSecuritiesCode(Character.getNumericValue(tmp)) );
		
		builder.setCharAt(3, String.valueOf( getRandom(1, 9)).charAt(0) );
		builder.setCharAt(4, extFirstKey.charAt(1));
		
		builder.setCharAt(key.length() - 3, extLastKey.charAt( extLastKey.length() -3 ) );
		builder.setCharAt(key.length() - 2, extLastKey.charAt( extLastKey.length() -2 ) );
		builder.setCharAt(key.length() - 1, extLastKey.charAt( extLastKey.length() -1 ) );
		
		return builder.toString();
		
	}
	/*
	 * Create Date: 2020-10-19
	 * Author: Dodo (rabbit.white at daum dot net)
	 * Core: Cross-Checker Security Logics
	 * License: GNU/GPL v3 Licenses (Dodo public Security Package)
	 */
	@SuppressWarnings("unlikely-arg-type")
	public static boolean compareCustomExtCheck(String basicToken, String usrToken) {
		
		boolean result = true;
		String key = generateMD5(basicToken);
		StringBuilder builder = new StringBuilder(key);
		
		int checkCode = -9;
		
		builder.setCharAt(0, extFirstKey.charAt(0));
		
		// 체크1
		if ( usrToken.substring(0).indexOf(extFirstKey.charAt(0)) > -1 ) {
			// System.out.println("참1");
		}else {
			result = false;
		}
		
		checkCode = getIntSecuritiesCode(usrToken.substring(1).charAt(0));
		System.out.println(usrToken.substring(1).charAt(0));
		
		// 체크2
		if ( checkCode >= 1 && checkCode <= 5 ) {
			// System.out.println("참2");
		}else {
			result = false;
		}

		// 체크3
		checkCode = getIntSecuritiesCode(usrToken.substring(2).charAt(0));
		if ( checkCode >= 1 && checkCode <= 5 ) {
			// System.out.println("참3");
		}else {
			result = false;
		}
		
		// 체크4
		if ( usrToken.substring(4).indexOf(extFirstKey.charAt(1)) > -1) {
			// System.out.println("참4");
		}else {
			result = false;
		}
		
		// 체크5
		if ( usrToken.substring(key.length() - 3).indexOf(extLastKey.charAt( extLastKey.length() -3 )) > -1){
			// System.out.println("참5");
		}else {
			result = false;
		}
		
		// 체크6
		if ( usrToken.substring(key.length() - 2).indexOf(extLastKey.charAt( extLastKey.length() -2 )) > -1){
			// System.out.println("참6");
		}else {
			result = false;
		}
		
		// 체크7
		if ( usrToken.substring(key.length() - 1).indexOf(extLastKey.charAt( extLastKey.length() -1 )) > -1){
			// System.out.println("참7");
		}else {
			result = false;
		}
		
		return result;
		
	}

	private static String hashString(String message, String algorithm, String charest) {

		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			
			byte[] hashedBytes = digest.digest(message.getBytes(charest));
			return convertByteArrayToHexString(hashedBytes);

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			return "";
		}

	}

	public static int getRandom(int from, int to){
		
		return (int)(Math.random()*((Math.abs(to-from))+1)) + Math.min(from, to);
		
	}
	
	private static char getCharSecuritiesCode(int choose) {
		
		switch ( choose ) {
			
			case 1:
				return 'l';
							
			case 2:
				return 'm';
	
			case 3:
				return 'a';
		
			case 4:
				return 'e';
				
			case 5:
				return 'k';
		
			case 6:
				return 'n';
		
			case 7:
				return 'p';
		
			case 8:
				return 'z';
		
			case 9:
				return 'q';
		
			default:
				return 'i';
				
		}
		
	}

	private static int getIntSecuritiesCode(char choose) {
		
		switch ( choose ) {
			
			case 'l':
				return 1;
							
			case 'm':
				return 2;
	
			case 'a':
				return 3;
		
			case 'e':
				return 4;
				
			case 'k':
				return 5;
		
			case 'n':
				return 6;
		
			case 'p':
				return 7;
		
			case 'z':
				return 8;
		
			case 'q':
				return 9;
		
			default:
				return -5;
				
		}
		
	}
	
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
		
	}
	
}
