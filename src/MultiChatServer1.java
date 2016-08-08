

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiChatServer {

	public static void main(String[] args) throws Exception {

		HashMap<String, String> clients = new HashMap<String, String>();

		List<Object> list = new ArrayList<>();

		ServerSocket serverSocket = new ServerSocket(8085);
		Socket socket = serverSocket.accept();
		System.out.println(socket.getInetAddress() + ":" + socket.getPort()); 

		DataInputStream din = new DataInputStream(socket.getInputStream());
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

		String readMsg = din.readUTF();

		InetAddress ip = socket.getInetAddress();
		String strIP = ip.toString();
		strIP = strIP.substring(1);

		clients.put(strIP, "A");
		list.add(clients.get(strIP));
		// System.out.println("strIP3:" + list.get(0)); // name get....

		System.out.println("Server readMsg : " + readMsg);

		System.out.println("Reply Msg");
		dout.writeUTF("your ip addresss : " + strIP + " : " + "your name : " + list.get(0) + " : "
				+ "\nServer Send MSG : " + readMsg);

		socket.close();
		serverSocket.close();

	}
}
