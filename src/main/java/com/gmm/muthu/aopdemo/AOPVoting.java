package com.gmm.muthu.aopdemo;

/*
 * Author: Muthu Mariyappan
 * Date : 02.07.2018
 * This is a Target object - object which advised by VoterAspect aspect in aop terms
 * It is considered as real world business logic
 * It doesn't aware of the logging that occurs in the background, so dont have to worry about 
 * modifying business logic to get/remove other functionalities such as logging
 * So it becomes more scalable and less dependent
 * */


class AOPVoting 
{
    //This method receives voter object and returns the same if no underage , else exception is thrown
	public Voter voteInElection(Voter voter)throws UnderAgeException{
    	if(voter.getAge()<18)// Creates and throws the exception with custom message - this message reflected in the underagedvotelog file
    		throw new UnderAgeException("Voter's name is "+voter.getName()+". And voter's age is "+voter.getAge()+".\n");
    	else{
    		return voter;// this reflected in validvotelog file
    	}		
    }
}