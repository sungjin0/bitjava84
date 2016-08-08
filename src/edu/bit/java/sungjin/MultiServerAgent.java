package edu.bit.java.sungjin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MultiServerAgent extends Thread {

	private String username;
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dos;
	private MultiSeverMain main;

	public MultiServerAgent(Socket socket, MultiSeverMain main) throws Exception {

		this.socket = socket;
		this.din = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
		this.main = main;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public void run() {

		while (true) {
			try {
				String msg = din.readUTF();
				main.broadcast(msg);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMsg(String msg) throws Exception {
		dos.writeUTF(msg);
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getDin() {
		return din;
	}

	public void setDin(DataInputStream din) {
		this.din = din;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

}
