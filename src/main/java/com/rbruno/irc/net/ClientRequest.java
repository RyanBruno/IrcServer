package com.rbruno.irc.net;

import com.rbruno.irc.client.Client;

public class ClientRequest extends Request {

	private Client client;

	public ClientRequest(ClientConnection connection, String line) throws Exception {
		super(connection, line);
		client = connection.getClient();
	}

	public Client getClient() {
		return client;
	}

}
