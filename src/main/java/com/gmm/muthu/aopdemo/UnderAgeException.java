package com.gmm.muthu.aopdemo;

/*
 * Author: Muthu Mariyappan
 * Date : 02.07.2018
 * This is user defined exception which gets thrown by AOPVoting if voter's age is below 18.
 * */

class UnderAgeException extends Exception{

	private static final long serialVersionUID = 1L; // For serialization, defaultly added
	private String message = null;
	
	UnderAgeException(String msg){ // Creating a exception with custom message
		this.message = msg;
	}
	
	@Override
	public String toString(){ // Overriding toString method to return the custom message
		return message;
	}
}