package com.rbruno.irc;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Runnable;

public class Server implements Runnable {

	public boolean running;
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		Thread run = new Thread(this, "Running Thread");
		run.start();
	}

	public void run() {
		while (running) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				Thread client = new Thread(new Client(socket));
				client.run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException {
		new Server(6667);
	}
}