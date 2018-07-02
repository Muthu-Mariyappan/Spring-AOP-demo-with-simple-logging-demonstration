package com.gmm.muthu.aopdemo;

/*
 * Author: Muthu Mariyappan
 * Date : 02.07.2018
 * It can be considered as a test file for AOPVoting or as user class which uses(call/execute) the AOPVoting class
 * It can be considered as introduction in aop terms  
 * Also the main class
 * It gets the number of voters and loops continuously getting name and age of voters till all voters are voted
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPVoteMainDemo {
	
	public static void main(String... s){
		
		//Accesses and parses the applicationcontext.xml to get the target object information
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
		//Gets the target object from application context
		AOPVoting voting = (AOPVoting)context.getBean("VotingBean");
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\t\t\tElection in Zootopia\n\nEnter the total voters count: ");
		int voterCount=0;
		
		try {
			voterCount = Integer.parseInt(bf.readLine()); //gets number of voters
			for(int i=1;i<=voterCount;i++){
				System.out.println("\nEnter Voter "+i);
				System.out.print("Voter Name : ");
				String name = bf.readLine();
				System.out.print("Voter Age : ");
				int age = Integer.parseInt(bf.readLine());
				
				try {
					voting.voteInElection(new Voter(name,age)); //uses target object- join point
					System.out.println("\n"+name+" successfully voted in zootopia election!"); //prints upon successful vote
				} catch (UnderAgeException e) {
					System.out.println("\n"+name+" couldn't vote due to underage. Age is "+age); //prints upon exception
				}
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error during voting input process");
			e.printStackTrace();
		}
	}
}
