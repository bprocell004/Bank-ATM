/****************
 * Name:          Brent Procell
 * Course:		  CIS 3970.O1I
 * Semester: 	  Spring 2019
 * Assignment:	  Lab 12
 * Date started:  5/02/2019
 * Date Finished: 5/05/2019
 * Description:  You are to write a Java program using the following instructions to build a bank-ATM server system with sockets that allows multiple concurrent users who could even be sharing accounts 
 ***************/

package bank;

//class to hold account information and make transaction types
public class Account {
	private double balance; //holds balance
	private int accountNum; //holds account number
	
	//constructor with balance argument
	public Account(double balance, int accountNum) {
		this.balance = balance;
		this.accountNum = accountNum;
	}
	//method to add amount passed in argument to balance and return
	public double deposit(double amount) {
		balance = balance + amount;
		return balance;
	}
	//method to subtract amount passed in argument to balance and return
	public double withdraw(double amount) {
		balance = balance - amount;
		return balance;
	}
	//method to return balance
	public double getBalance() {
		return balance;
	}
	//method to compare private account number to account number passed through argument.
	public boolean equals(int accountNum) {
		if (this.accountNum == accountNum) {
			return true;
		}
		else {
			return false;
		}
	}
}
