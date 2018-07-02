package com.gmm.muthu.aopdemo;

/*
 * Author: Muthu Mariyappan
 * Date : 02.07.2018
 * This class represents the voter - simple type class
 * */

class Voter {
	
	private String name;
	private int age;
	
	//Making default constructor private
	private Voter(){} //Forces to use parameterized constructor
	
	Voter(String name,int age){ // voter with namd and age
		this.name = name;
		this.age = age;
	}
	
	// getter methods
	public String getName(){
		return this.name;
	}
	
	public int getAge(){
		return this.age;
	}
	
	@Override
	public String toString(){ // Handy when printing object
		return ("[ Voter Name : "+this.name+"; Voter Age : "+this.age+"; ] ");
	}
	
}
