package edu.bit.java.sungjin;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiSeverMain {

	private ServerSocket serverSocket;

	private Map<String, MultiServerAgent> agentMap;
	private List<MultiServerAgent> agentList;

	public MultiSeverMain() throws Exception {
		init();
	}

	private void init() throws Exception {

		serverSocket = new ServerSocket(8085);

		agentMap = new HashMap<>();
		agentList = new ArrayList<>();

		while (true) {

			Socket socket = serverSocket.accept();
			MultiServerAgent agent = new MultiServerAgent(socket, this);
			agentList.add(agent);
			agent.start();

		}
	}

	public void broadcast(String msg) throws Exception {

		for (MultiServerAgent multiServerAgent : agentList) {
			multiServerAgent.sendMsg(msg);
		}

	}

}
