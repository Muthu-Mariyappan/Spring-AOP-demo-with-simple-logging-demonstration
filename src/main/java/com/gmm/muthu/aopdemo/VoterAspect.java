package com.gmm.muthu.aopdemo;

import org.aspectj.lang.annotation.Aspect;

/*
 * Author: Muthu Mariyappan
 * Date : 02.07.2018
 * This is an Aspect in aop terms
 * This class takes care all the logging for the business logic, and the business login AOPVoting is completely blind to this loggin
 * It has 3 advices - After, after return and after throwing
 * More information about advices can be found below
 * */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;

//Aspect containing three advises
@Aspect
public class VoterAspect {

	private final String TOTAL_VOTE_COUNT_LOG_PATH = "L:\\eclipse-java-neon-3-RC3-win32\\eclipse\\Neonite\\aopdemo\\src\\main\\java\\com\\gmm\\muthu\\aopdemo\\totalvotecount";
	private final String UNDERAGED_VOTE_LOG_PATH = "L:\\eclipse-java-neon-3-RC3-win32\\eclipse\\Neonite\\aopdemo\\src\\main\\java\\com\\gmm\\muthu\\aopdemo\\underagedvotelog";
	private final String VALID_VOTE_LOG_PATH = "L:\\eclipse-java-neon-3-RC3-win32\\eclipse\\Neonite\\aopdemo\\src\\main\\java\\com\\gmm\\muthu\\aopdemo\\validvotelog";
	
	//It is a pointcut expression which matches the join point voteInElection which is in target object AOPVoting
	private final String POINTCUT_EXPRESSION = "execution(* AOPVoting.vote*(..))"; 
	// execution is one of join point and only the supported in spring, within is also one of join point
	
	
	@Pointcut(POINTCUT_EXPRESSION)//One way of matching pointcut expression with advice
	public void va(){} // va acts as pointcut expression's reference
	
	
	/*
	 * This method logs the number of votes who appeared on all elections irrespective of voting result(valid/invalid)
	 * It uses totalvotecount log file to keep the count, initially reset the file to zero if file is empty
	 * */
	@After("va()") // referring to pointcut expression - and advices after method is executed
	public void logTotalVoteCount(JoinPoint jp){
		
		Scanner sn = null;
		FileWriter fwriter=null;
		
		try{
			sn = new Scanner(new File(TOTAL_VOTE_COUNT_LOG_PATH)); //opens the file to read
			if(!sn.hasNext()){ // if file is blank then initializes file with 0 for successive increments
				sn.close();
				fwriter = new FileWriter(TOTAL_VOTE_COUNT_LOG_PATH);
				fwriter.write(new Integer(0).toString());
				fwriter.close(); // close and open since a file cannot be both open and closed
				sn = new Scanner(new File(TOTAL_VOTE_COUNT_LOG_PATH));
			}
			int count = sn.nextInt(); //reads the old count
			sn.close();
			
			fwriter = new FileWriter(TOTAL_VOTE_COUNT_LOG_PATH); //opens file to write
			fwriter.write(new Integer(count+1).toString()); // increments for every appearance of voter
			fwriter.close();
		}
		catch(IOException e){
			System.out.println("I/O error during access of totalvotecount file");
			e.printStackTrace();
		}
		finally{
			try {
				sn.close();
				fwriter.close();
			} catch (IOException e) {
				System.out.println("Error closing log file!");
			}
		}
	}
	
	
	/*
	 * This method logs voter detail in validvotelog file if the voter is successuflly voted 
	 * vote info is obtained using returning parameter in AfterReturning annotation
	 * */
	@AfterReturning(pointcut=POINTCUT_EXPRESSION,returning = "successfulVote") // After returning advice
	public void logSuccessfulVotes(JoinPoint jp,Object successfulVote){ // returning in annotation and variable name of 2nd parameter should be same

		FileWriter fwriter=null;
		
		try{

			fwriter = new FileWriter(VALID_VOTE_LOG_PATH,true); // opens file in append mode
			fwriter.write(successfulVote.toString());
		}
		catch(IOException e){
			System.out.println("I/O error during access of validvotelog file");
			e.printStackTrace();
		}
		finally{
			try {
				fwriter.close();
			} catch (IOException e) {
				System.out.println("Error closing log file!");
			}
		}
		
	}
	
	/*
	 * This method logs voter detail in underagedvotelog file if the voter is underaged
	 * vote info is obtained using thrown excpeion in AfterThrowing annotation
	 * */
	
	@AfterThrowing(pointcut = POINTCUT_EXPRESSION,  throwing= "underagedVote") // After Throwing advice
	public void logUnderageVotes(JoinPoint jp,Throwable underagedVote){
		
		FileWriter fwriter=null;
		
		try{

			fwriter = new FileWriter(UNDERAGED_VOTE_LOG_PATH,true); // opens file in append mode
			fwriter.write(underagedVote.toString());
		}
		catch(IOException e){
			System.out.println("I/O error during access of underagedvotelog file");
			e.printStackTrace();
		}
		finally{
			try {
				fwriter.close();
			} catch (IOException e) {
				System.out.println("Error closing log file!");
			}
		}
	}
}