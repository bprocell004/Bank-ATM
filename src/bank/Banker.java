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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

//main class that sets the account objects, server object and start the server.
public class Banker {

	public static void main(String[] args) throws IOException {
		ArrayList<Account> account1 = new ArrayList<Account>(); //array to hold accounts
		int accountNum; //hold account number
		int balance; //hold account balance
		
		FileInputStream yes = new FileInputStream("accounts.data"); //read in accounts.data file
		DataInputStream inputStream = new DataInputStream(yes); //wrapper to read in data
		
		//loop to run through the entire file
		for(int i = 0; i < 17; ++i) {
			accountNum = inputStream.readInt(); //read the next int and assign it the account number
			balance = inputStream.readInt(); //read the next int and assign it the balance of the account number
		account1.add( new Account(balance, accountNum) ); //new account object to arraylist
		}
		inputStream.close(); //close stream

		AccountServer server1 = new AccountServer(4020); //new server object
		server1.startServer(account1); //start server with the account object passed through as argument.
	}

}
