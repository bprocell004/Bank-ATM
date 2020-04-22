/****************
 * Name:          Brent Procell
 * Course:		  CIS 3970.O1I
 * Semester: 	  Spring 2019
 * Assignment:	  Lab 12
 * Date started:  5/02/2019
 * Date Finished: 5/05/2019
 * Description:  You are to write a Java program using the following instructions to build a bank-ATM server system with sockets that allows multiple concurrent users who could even be sharing accounts 
 ***************/

package transaction;

import java.io.Serializable;

//transaction class that implements Serializable class. Determines transaction and amount to be passed through sockets
public class Transaction implements Serializable {
	
	private static final long serialVersionUID = -3460525292990335254L; //same serial number that is from client transaction class
	private double amount; //holds amount money
	private String transactionType; //holds transaction type
	private int accountNum; //holds account number

	//Constructor to take in arguments and save them into variables
	public Transaction(int type, double amount, int accountNum) {
		this.amount = amount;
		this.accountNum = accountNum;
		//if else statement to take number passed and set transaction type based on number passed
		if (type == 1) {
			transactionType = "deposit";
		}
		else if (type == 2) {
			transactionType = "withdrawal";
		}
		else {
			transactionType = "retrieve balance";
		}
	}
	
	//method to get transaction type
	public String getTransactionType() {
		return transactionType;
	}
	
	//method to get amount of money
	public double getAmount() {
		return amount;
	}
	//method to get account number
	public int getID() {
		return accountNum;
	}
}