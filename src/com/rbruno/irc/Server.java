package com.rbruno.irc;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Runnable;

import com.rbruno.irc.net.Manager;

public class Server implements Runnable {
	
	private static Server server;
	
	private int port;
	private boolean running;
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		server = this;
		
		serverSocket = new ServerSocket(port);
		Thread run = new Thread(this, "Running Thread");
		run.start();
	}

	public void run() {
		while (running) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				Thread client = new Thread(new Manager(socket));
				client.run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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