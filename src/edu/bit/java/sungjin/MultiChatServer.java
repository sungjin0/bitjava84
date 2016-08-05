package edu.bit.java.sungjin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class MultiChatServer {

	private static final int SERVER_PORT = 8085;
	private ServerSocket serverSocket;
	private Socket socket;
	public static int user_count = 0;

	public void init() throws Exception {

		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Server Start");
		try {
			while (true) {
				socket = serverSocket.accept();
				user_count++;
				Thread sr = new Thread(new ServerRecvThread(socket));
				sr.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		MultiChatServer multiChat = new MultiChatServer();
		multiChat.init();
	}

	class ServerRecvThread extends Thread {

		private DataInputStream din;
		private DataOutputStream dout;
		private Socket socket;
		private String name;
		private String str_ip;
		private int port;
		private HashMap<String, String> client_map = new HashMap<String, String>();

		ServerRecvThread(Socket socket) throws Exception {

			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			// InetAddress ip = socket.getInetAddress();
			str_ip = socket.getInetAddress().toString().substring(1);
			port = socket.getPort();
			name = din.readUTF();

		}

		public void showClients() throws Exception {

			HashMap<String, String> clients = this.client_map;
			TreeMap<String, String> tree_clients = new TreeMap<>(clients);

			Iterator<String> tree_keys = tree_clients.keySet().iterator();

			System.out.println("=====current users list=====" + "the total number : " + MultiChatServer.user_count);
			while (tree_keys.hasNext()) {
				String key = tree_keys.next();
				System.out.println("IP Address : " + key + " Name : " + clients.get(key));
			}
		}

		public void sendToAll(String read_msg) throws Exception {

			HashMap<String, String> clients = this.client_map;
			TreeMap<String, String> tree_clients = new TreeMap<>(clients);
			Iterator<String> tree_keys = tree_clients.keySet().iterator();

			while (tree_keys.hasNext()) {
				String key = tree_keys.next();
				// clients.get(key); //name
				this.dout.writeUTF("모두에게 전달 : " + read_msg);
			}
		}

		@Override
		public void run() {

			while (din != null) {
				try {

					client_map.put(str_ip, this.name);
					showClients();
					String read_msg = din.readUTF();

					sendToAll(read_msg);

				} catch (Exception e) {
					try {
						din.close();
						dout.close();
						socket.close();
						e.printStackTrace();
					} catch (Exception e1) {
						System.out.println(this.str_ip + " : 연결 끊김");
						return;
					}
				}
			} // end while
		}// end run
	}

}
