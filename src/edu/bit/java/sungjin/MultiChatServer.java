package edu.bit.java.sungjin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Clock;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.xml.sax.helpers.ParserAdapter;

public class MultiChatServer {

	private static final int SERVER_PORT = 8085;
	private ServerSocket serverSocket;
	private Socket socket;
	public static int user_count;
	private HashMap<String, Socket> client_map;

	MultiChatServer() throws Exception {

		client_map = new HashMap<String, Socket>();
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Server Start");
	}

	public void init() throws Exception {

		try {
			while (true) {
				socket = serverSocket.accept();
				user_count++;
				Thread srt = new ServerRecvThread(socket);
				srt.start();
			}
		} catch (Exception e) {
			return;
		}
	}

	public static void main(String[] args) throws Exception {

		MultiChatServer multiChat = new MultiChatServer();
		multiChat.init();
	}

	class ServerRecvThread extends Thread {

		private DataInputStream din;
		// private DataOutputStream dout;
		private Socket socket;
		private String name;

		ServerRecvThread(Socket socket) throws Exception {

			din = new DataInputStream(socket.getInputStream());
			// dout = null;
			this.name = din.readUTF();
			client_map.put(this.name, socket);
			sendToAll(this.name);
		}

	/*	for Server debug
		public void showList(String parse_msg) throws Exception {

			Iterator<String> keys = client_map.keySet().iterator();

			System.out.println("=====current users list=====" + "the total number : " + MultiChatServer.user_count);
			while (keys.hasNext()) {
				String key = keys.next();
				System.out.println("Name : " + key + " socket info : "
						+ client_map.get(key).getInetAddress().toString().substring(1));
				 System.out.println("Server_Test parse_msg : " + parse_msg);
			}
		}*/

		public void showList(String parse_msg) throws Exception {

			Set<String> keys_data = client_map.keySet();
			Iterator<String> key_iter = keys_data.iterator();

			while (key_iter.hasNext()) {
				String key = key_iter.next();
				DataOutputStream data_out = new DataOutputStream(client_map.get(key).getOutputStream());
				data_out.writeUTF("=====current users list=====" + "the total number : " + MultiChatServer.user_count);
				data_out.writeUTF(keys_data.toString().substring(0));
			}
		}

		public void sendToOne(String fromName, String toName, String parse_msg) throws Exception {

			// System.out.println("check" + fromName + ":" + toName + ":" + parse_msg);
			DataOutputStream data_out = new DataOutputStream(client_map.get(toName).getOutputStream());
			data_out.writeUTF("Message from [" + fromName + "] on OTO : " + parse_msg);

		}

		public void sendToAll(String parse_msg) throws Exception {

			Iterator<String> keys = client_map.keySet().iterator();

			try {

				while (keys.hasNext()) {
					String key = keys.next();
					DataOutputStream data_out = new DataOutputStream(client_map.get(key).getOutputStream());

					if (parse_msg == name) {
						if (parse_msg == key)
							data_out.writeUTF("[*" + this.name + "]님이 입장하셨습니다.");
						else
							data_out.writeUTF("[" + this.name + "]님이 입장하셨습니다.");
					} else if (parse_msg.endsWith("/exit") && (this.name != key)) {
						data_out.writeUTF("[" + name + "]" + "님이 종료하셨습니다. ");
					} else if (parse_msg.startsWith("/oto")) {
						String[] split_msg = parse_msg.split(" ");
						if (split_msg.length <= 2 && this.name == key) {
							data_out.writeUTF("How to use One to One : /oto username message");
							return;
						} else {
							sendToOne(key, split_msg[1], split_msg[2]);
							return;
						}
					} else if (this.name == key) {
						data_out.writeUTF("[*" + this.name + "] send to [All] : " + parse_msg);
					} else
						data_out.writeUTF("received Message From [" + this.name + "] : " + parse_msg);
				} // end while

			} catch (Exception e) {

			}
		}

		@Override
		public void run() {

			while (din != null) {
				try {

					String read_msg = din.readUTF();
					String parse_msg = read_msg.substring(read_msg.indexOf("]") + 1);
					showList(parse_msg);
					sendToAll(parse_msg);

				} catch (Exception e) {
					try {
						din.close();
						// dout.close();
						socket.close();
						e.printStackTrace();
					} catch (Exception e1) {
						user_count--;
						client_map.remove(name);
						return;
					}
				}
			} // end while
		}// end run
	}// end ServerRecvThread
}