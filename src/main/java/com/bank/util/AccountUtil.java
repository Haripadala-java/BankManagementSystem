package com.bank.util;

import java.util.Random;

public class AccountUtil {
	
	private static Random random = new Random();
	
	public static String generateAccountNumber() {
	     long number = 1000000000L + (long)(random.nextDouble() * 9000000000L);
		return "10"+number;
	}
	
	public static String tempPassword() {
	      return "TMP" + (1000 + random.nextInt(9000));
	}

}
