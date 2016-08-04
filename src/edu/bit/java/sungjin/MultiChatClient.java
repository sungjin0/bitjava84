package edu.bit.java.sungjin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {

	private static final int SERVER_PORT = 8085;
	private static final String SERVER_IP = "localhost";

	private static Socket socket;

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("how to execute : java MultiChatClient username");
			System.exit(0);
		}

		try {
			socket = new Socket(SERVER_IP, SERVER_PORT);
			System.out.println("client connection");

			Thread sender = new Thread(new ClientSender(socket, args[0]));
			Thread receiver = new Thread(new ClientReceiver(socket));

			sender.start();
			receiver.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static class ClientSender extends Thread {

		private Socket socket;
		private String name;
		private DataOutputStream dout;

		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				this.name = name;
				dout = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			Scanner keyboard = new Scanner(System.in);

			try {
				if (dout != null) {
					dout.writeUTF(name);
				}
				while (dout != null) {
					dout.writeUTF("[" + this.name + "]" + keyboard.nextLine());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static class ClientReceiver extends Thread {

		private Socket socket;
		private DataInputStream din;

		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				din = new DataInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (din != null) {
				try {
					System.out.println(din.readUTF());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
