package com.hackerrank.sample.dto;

public class FilteredProducts {

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		barCode = barCode;
	}

	private String barCode;
	
	public FilteredProducts(String a) {
		barCode = a;
	}


}