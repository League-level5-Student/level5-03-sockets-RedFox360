package _02_Chat_Application;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server extends Thread {
	// 1. Create an object of the ServerSocket class
	ServerSocket server;

	public Server() throws IOException {
		// 2. Initialize the ServerSocket object. In the parameters,
		// you must define the port at which the server will listen for connections.
		server = new ServerSocket(8080);
		// *OPTIONAL* you can set a time limit for the server to wait by using the
		// ServerSocket's setSoTimeout(int timeInMilliSeconds) method
		server.setSoTimeout(6000);
	}

	public void run() {
		// 3. Create a boolean variable and initialize it to true.
		boolean loop = true;
		// 4. Make a while loop that continues looping as long as the boolean created in
		// the previous step is true.
		Scanner scan = new Scanner(System.in);
		
		Socket socket = null;

		
		try {
			socket = server.accept();
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			Thread thread = new Thread(() -> {
				boolean l = true;
				while (l) {
				try {
					System.out.println("Message from client: " + inputStream.readUTF());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					l = false;
				}
				}
			});
			thread.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (loop) {
			// 5. Make a try-catch block that checks for two types Exceptions:
			// SocketTimeoutException and IOException.
			// Put steps 8 - 15 in the try block.
			try {
				// 8. Let the user know that the server is waiting for a client to connect.
				// 9. Create an object of the Socket class and initialize it to
				// serverSocket.accept();
				// Change serverSocket to match the ServerSocket member variable you created in
				// step 1.
				// The program will wait her until either a client connects or the timeout
				// expires.
				// 10. Let the user know that the client has connected.
				// 11. Create a DataInputStream object. When initializing it, use the Socket
				// object you created in step 9 to call the getInputStream() method.
				// 12. Print the message from the DataInputStream object using the readUTF()
				// method
				// 13. Create a DataOutputStream object. When initializing it, use the Server
				// object you created in step 9 to call the getOutputStream() method.
				DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
				// 14. Use the DataOutputStream object to send a message to the client using the
				// writeUTF(String message) method.
				String msg = scan.nextLine();
				if (msg.equalsIgnoreCase("quit")) {
					loop = false;
				}
				outputStream.writeUTF(msg);
				// 15. Close the client server

			} catch (SocketTimeoutException e) {
				// 6. If the program catches a SockeTimeoutException, let the user know about it
				// and set loop's boolean variable to false.
				System.out.println("A socket timeout exception occured!");
				loop = false;
			} catch (IOException e) {
				// 7. If the program catches a IOException, let the user know about it and set
				// the loop's boolean variable to false.
				System.out.println("An IOException occured!");
				loop = false;
			} 
		}

		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.close();

	}

	public static void main(String[] args) {
		// 16. In a new thread, create an object of the ServerGreeter class and start
		// the thread. Don't forget the try-catch.
		try {
			new Server().start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
