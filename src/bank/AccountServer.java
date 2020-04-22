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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import transaction.Transaction;
//class to start the server socket and establish a connection with a client socket then start a thread
public class AccountServer {
	private SSLServerSocket server;	// secure server socket
	private static String algorithm = "SSL"; //SSL algorithem
	
	//constructor to create serversocket and have it set to port from argument.
	public AccountServer(int port) {
		try {
			//pw = Lith2ump
			//char [] password = System.console().readPassword("Password: ");
			char [] password = {'L', 'i', 't', 'h', '2', 'u', 'm', 'p'}; //pw for key.
			
			//instantiate key store with JKS algorithm
			KeyStore keyS = KeyStore.getInstance("JKS");
			keyS.load(new FileInputStream("jnp4e.keys"), password); //read in key file
			
			//instantiate key manager factory with SunX509 algorithm
			KeyManagerFactory keyManFact = KeyManagerFactory.getInstance("SunX509");
			keyManFact.init(keyS, password);
			KeyManager[] km = keyManFact.getKeyManagers(); //key manager array
			
			Arrays.fill(password, '0'); //clear password
			
			// instantiate trust manager factory object with SunX509
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(keyS);
			TrustManager[] tm = tmf.getTrustManagers(); //trust manager array
			
			//ssl context object with SSL algorithm
			SSLContext sslContext = SSLContext.getInstance(algorithm);
			sslContext.init(km,  tm, null);
			
			//ssl server socket factory object
			SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
			server = (SSLServerSocket) factory.createServerSocket(port); //secure server socket
			
		} catch (IOException e) {
			System.out.println("Unable to start server on port " + port);
			System.out.println(e);
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//method to start server socket and have it wait to be connected to by client socket.
	public void startServer(ArrayList<Account> account1) {
		System.out.println("Waiting for connection...");
		//loop to keep server socket running and wait for more client connections
		while(true) {
			try {
				SSLSocket client = (SSLSocket) server.accept(); //accept connection from client socket
				client.setEnabledCipherSuites(client.getSupportedCipherSuites()); 
				
				client.startHandshake(); //start handshake
				SSLSession sslSession = client.getSession(); //instantiate ssl session
				
				System.out.println("Client connected: Start SSL Session...");
				System.out.println("\tProtocol : " + sslSession.getProtocol()); //display session protocol to console
				System.out.println("\tCipher suite : " + sslSession.getCipherSuite()); //display cipherSuite after a connection.
				
				ClientHandler task = new ClientHandler(client, account1); //create clienthandler object
				task.start(); //start thread
				//throw exceptions
			} catch (IOException e) {
				System.out.println("Connection attempt failed.");
				e.printStackTrace();
			}
		}
	}
	//stop server method.
	public void stopServer() {
		try {
			server.close();
			System.out.println("Server is stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}

