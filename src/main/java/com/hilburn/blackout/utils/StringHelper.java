package com.hilburn.blackout.utils;

import java.text.DecimalFormat;

public class StringHelper {

	public static String addCommas(long input)
	{
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		return myFormatter.format(input);
	}
	
	
}
