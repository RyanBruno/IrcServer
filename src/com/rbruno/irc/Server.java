package com.rbruno.irc;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Runnable;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.net.Client;
import com.rbruno.irc.net.Connection;

public class Server implements Runnable {

	private static Server server;

	private int port;
	private boolean running;
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		server = this;

		Command.init();
		serverSocket = new ServerSocket(port);
		Thread run = new Thread(this, "Running Thread");
		run.start();
	}

	public void run() {
		while (running) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				Thread client = new Thread(new Connection(socket));
				client.run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addClient(Client client) {
		//TODO:
	}

	public static void main(String args[]) throws IOException {
		new Server(6667);
	}

	public static Server getServer() {
		return server;
	}

	public int getPort() {
		return port;
	}
}