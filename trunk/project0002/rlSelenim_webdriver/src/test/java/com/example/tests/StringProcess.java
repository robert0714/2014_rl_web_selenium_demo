package com.example.tests;

import org.apache.commons.lang3.StringUtils;

public class StringProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String txId = "TXFLRL1307081334293211";
		String answer = StringUtils.left(txId, 10);
//		
		System.out.println(answer.toString());
		System.out.println(StringUtils.substring(txId, 4, 6));
	}

}
