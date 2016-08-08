

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MultiChatClient {

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("localhost", 8085);
		System.out.println("client connection request");
		
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		DataInputStream din = new DataInputStream(socket.getInputStream());

		dout.writeUTF("client send!!!");

		String readMsg = din.readUTF();
		System.out.println("Client Recevie MSG: " + readMsg);

		dout.close();
		socket.close();
	}

}
