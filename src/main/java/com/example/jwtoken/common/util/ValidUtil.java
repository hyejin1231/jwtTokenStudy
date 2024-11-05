package com.example.jwtoken.common.util;

public class ValidUtil
{
	public static boolean checkPhoneNo(String phoneNo)
	{
		return phoneNo.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$");
	}
}
