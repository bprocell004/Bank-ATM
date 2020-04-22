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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.SSLSocket;

import transaction.Transaction;
//class to establish a thread and complete a transaction for the bank.

public class ClientHandler extends Thread {
	private SSLSocket client; //hold client connection
	private ObjectInputStream input; //reads client message
	private ObjectOutputStream output; //sends server message
	private static ArrayList<Account> account; // hold arraylist of accounts
	
	//constructor for clienthandler class with client socket and account object array as argument
	ClientHandler(Socket connection, ArrayList<Account> account1) {
		this.client = (SSLSocket) connection;
		this.account = account1;
	}
	
	//override run method to run a thread and make the transaction complete if it is a correct account number
	@Override
	public void run() {
	try {
	//set the input and output streams
	this.input = new ObjectInputStream(client.getInputStream());
	this.output = new ObjectOutputStream(client.getOutputStream());
	//while(true) {
	// get the object and make transaction object from it.
	Object incoming = this.input.readObject();
	Transaction approach = (Transaction) incoming;

	//loop to go through the account array
	for (int i = 0; i < account.size(); ++i) {
		//call the equal method to compare array account number to number passed through socket
		if (account.get(i).equals(approach.getID()) == true) {
		//complete the transaction based off of the transaction type passed by client
			if(approach.getTransactionType().equals("deposit")) {
				account.get(i).deposit(approach.getAmount());
				String message = "Transaction Complete: Balance is $" + account.get(i).getBalance();
				this.output.writeObject(message);
				this.output.flush();
				break; // leave loop
			} 
			else if (approach.getTransactionType().equals("withdrawal")) {
				account.get(i).withdraw(approach.getAmount());
				String message = "Transaction Complete: Balance is $" + account.get(i).getBalance();
				this.output.writeObject(message);
				this.output.flush();
				break; //leave loop
			}
			else if (approach.getTransactionType().equals("retrieve balance")) {
				account.get(i).getBalance();
				String message = "Transaction Complete: Balance is $" + account.get(i).getBalance();
				this.output.writeObject(message);
				this.output.flush();
				break; //leave loop
			}
			else {
				String message = ("Incorrect Transaction type");
				this.output.writeObject(message);
				this.output.flush();
			}
		}
	
		
	}
//	}
		} catch (IOException e) {
		e.printStackTrace();
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
}
}
}
