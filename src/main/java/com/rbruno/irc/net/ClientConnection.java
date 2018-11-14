package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.commands.client.ClientCommand;
import com.rbruno.irc.logger.Logger;

public class ClientConnection extends Connection implements Runnable {

	private Client client;
	private BufferedReader reader;

	public ClientConnection(Socket socket, Client client, Server server, BufferedReader reader) {
		super(socket, server);
		this.client = client;
		this.reader = reader;
	}

	/**
	 * Start listing on socket. When a line is received it creates a new Request
	 * object and send to Command.runCommnd(Request)
	 */
	@Override
	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(getSocket().getInputStream(), "UTF-8"));
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
					e.printStackTrace();
					continue;
				}
				try {
					ClientCommand.runCommand(request);
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