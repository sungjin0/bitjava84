package edu.bit.java.sungjin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class MultiChatServer {
1
	private static final int SERVER_PORT = 8085;
	public static int user_count = 0;
	private ServerSocket server_socket;
	private Socket socket;

	public void init() throws Exception {

		server_socket = new ServerSocket(SERVER_PORT);
		System.out.println("Server Start");
		try {
			while (true) {
				socket = server_socket.accept();
				user_count++;
				Thread srt = new Thread(new ServerRecvThread(socket));
				srt.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		MultiChatServer multi_chat = new MultiChatServer();
		multi_chat.init();
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

			HashMap<String, String> clients = client_map;
			Iterator<String> keys = clients.keySet().iterator();

			while (keys.hasNext()) {
				String key = keys.next();
				// clients.get(key);//name
				// this.dout.writeUTF("sendToAll : " + read_msg);
				dout.writeUTF("sendToAll : " + read_msg);
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
					e.printStackTrace();
				}
			} // end while
		}// end run
	}// end ServerRecvThread
}
