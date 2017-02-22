package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.commands.Command;
import com.rbruno.irc.logger.Logger;

public class ClientConnection extends Connection implements Runnable {

	private Client client;

	public ClientConnection(Socket socket, Client client, Server server) {
		super(socket, server);
		this.client = client;
	}

	/**
	 * Start listing on socket. When a line is received it creates a new Request
	 * object and send to Command.runCommnd(Request)
	 */
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getSocket().getInputStream(), "UTF-8"));
			while (isOpen()) {
				String line = null;
				try {
					line = reader.readLine();
				} catch (Exception e) {
					if (e.getMessage() != null) if (e.getMessage().contains("Connection reset")) {
						this.close();
						continue;
					}
				}
				if (getServer().getConfig().getProperty("debug").equals("true")) Logger.log(line, Level.FINE);
				if (line == null) {
					close();
					continue;
				}
				ClientRequest request = null;
				try {
					request = new ClientRequest(this, line);
				} catch (Exception e) {
					Logger.log(getSocket().getInetAddress() + " sent an unparseable line: " + line, Level.FINE);
					continue;
				}
				try {
					Command.runCommand(request);
				} catch (Exception e) {
					Logger.log(getSocket().getInetAddress() + " ran a command that resulted in an error: " + line, Level.FINE);
					e.printStackTrace();
				}

			}
			reader.close();
		} catch (IOException e) {
			Logger.log(getSocket().getInetAddress() + " closed in an error state.", Level.FINE);
			this.close();
		}
	}

	public Client getClient() {
		return client;
	}


}
