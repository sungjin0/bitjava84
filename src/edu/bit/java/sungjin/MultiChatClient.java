package edu.bit.java.sungjin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {

	private static final int SERVER_PORT = 8085;
	private static final String SERVER_IP = "localhost";
	private static Socket socket;
	private Thread sender;
	private Thread receiver;

	MultiChatClient(String args) throws Exception {

		socket = new Socket(SERVER_IP, SERVER_PORT);
		System.out.println("client connection");

		sender = new ClientSender(socket, args);
		receiver = new ClientReceiver(socket);
	}

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("How to execute : java MultiChatClient username");
			return;
		} else {
			MultiChatClient mcc = new MultiChatClient(args[0]);
			try {
				mcc.sender.start();
				mcc.receiver.start();
			} catch (Exception e) {
				return;
			}
		}
	}

	static class ClientSender extends Thread {

		private Socket socket;
		private String name;
		private DataOutputStream dout;
		private Scanner scanner = new Scanner(System.in);

		ClientSender(Socket socket, String name) {

			this.socket = socket;
			this.name = name;

			try {
				dout = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			String keyboard;

			try {
				if (dout != null)
					dout.writeUTF(name);

				while (dout != null) {
					keyboard = scanner.nextLine();
					if (keyboard.endsWith("/exit")) {
						dout.writeUTF(keyboard);
						System.exit(0);
						/*
						 * Do not remove. this is that The Client Information Of
						 * The Abnormal System Status alerts to The Server. The
						 * alerted ServerThread performs Exception e1. Example)
						 * user_count--; client_map.remove(name);
						 * 
						 * and therefore, here Don't use "return;" that The
						 * normal status.
						 */
					}
					dout.writeUTF("[" + this.name + "]" + keyboard);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("ClientSender Info : Server Exit ");
				return;
			}
		}// end run
	}

	static class ClientReceiver extends Thread {

		private Socket socket;
		private DataInputStream din;

		ClientReceiver(Socket socket) {

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
					// e.printStackTrace();
					System.out.println("ClientReceiver Info : Server Exit ");
					return;
				}
			}
		}// end run
	}
}