package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// 1. Create a String for the ip address of the server.
		Scanner scan = new Scanner(System.in);
		String ip = "192.168.86.199";
		// If you don't know how to find a computer's ip address, ask about ifconfig on
		// linux/mac and ipconfig on windows.

		// 2. Create an integer for the server's port number
		int port = 8080;
		// 3. Surround steps 4-9 in a try-catch block that catches any IOExceptions.

		Socket socket = new Socket(ip, port);
		DataInputStream inputStream = new DataInputStream(socket.getInputStream());
		Thread thread = new Thread(() -> {
			boolean l = true;
			while(l) {
			try {
				System.out.println("Message from server: " + inputStream.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				l = false;
			}
			}
		});
		thread.start();
		boolean loop = true;
		while (loop) {
			try {
				// 4. Create an object of the Socket class. When initializing the object, pass
				// in the ip address and the port number
				// 5. Create a DataOutputStream object. When initializing it, use the Socket
				// object you created in step 4 to call the getOutputStream() method.
				DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
				// 6. Use the DataOutputStream object to send a message to the server using the
				// writeUTF(String message) method
				String msg = scan.nextLine();
				if (msg.equalsIgnoreCase("quit")) {
					loop = false;
				}
				outputStream.writeUTF(msg);
				// 7. Create a DataInputStream object. When initializing it, use the Server
				// object you created in step 4 to call the getInputStream() method.
				// 8. Use the DataInputStream object to print a message from the server using
				// the readUTF() method.
				// 9. Close the client's server object
			} catch (Exception e) {
				e.printStackTrace();
				loop = false;
			}
		}
		socket.close();
		scan.close();
	}
}
