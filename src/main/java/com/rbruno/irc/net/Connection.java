package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewClientCommandEvent;
import com.rbruno.irc.events.NewLineEvent;
import com.rbruno.irc.events.NewRegCommandEvent;
import com.rbruno.irc.events.NewRequestEvent;
import com.rbruno.irc.events.NickSetEvent;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Connection extends EventListener {

	private EventDispacher eventDispacher;
	protected Socket socket;

	private Client client;
	private Optional<String> nickname = Optional.empty();

	public Connection(EventDispacher eventDispacher, Socket socket) {
		this.eventDispacher = eventDispacher;
		this.socket = socket;
		eventDispacher.registerListener(this);

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			while (!socket.isClosed()) {
				String line = reader.readLine();

				if (line == null) {
					break;
				}

				eventDispacher.dispach(new NewLineEvent(line, Optional.ofNullable(client)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO QUIT event
	}

	@Override
	public void onNewLine(NewLineEvent event) {
		eventDispacher.dispach(new NewRequestEvent(new Request(this, event.getLine()), event.getClient()));
	}

	@Override
	public void onNewRequest(NewRequestEvent event) {
		if (client == null) {
			eventDispacher.dispach(new NewRegCommandEvent(event.getRequest()));
		} else {
			eventDispacher.dispach(new NewClientCommandEvent(event.getRequest(), client));
		}
	}
	
	@Override
	public void onNickSet(NickSetEvent event) {
		if (event.getConnection() == this) {
			nickname = Optional.of(event.getNickname());
		}
	}

	public boolean send(byte[] block) {

		try {
			socket.getOutputStream().write(block);
			socket.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean send(String message) {
		if (socket.isClosed())
			return false;
		System.out.println(message);
		return send(message.concat("\r\n").getBytes());
	}

	public boolean send(String prefix, String command, String args) {
		String message = ":" + prefix + " " + command + " " + args;
		return send(message);
	}

	public boolean send(int code, String nickname, String args) {
		String stringCode = code + "";
		if (stringCode.length() < 2)
			stringCode = "0" + stringCode;
		if (stringCode.length() < 3)
			stringCode = "0" + stringCode;

		String message = ":" + Server.getServer().getConfig().getHostname() + " " + stringCode + " " + nickname + " "
				+ args;
		return send(message);
	}

	public boolean send(Reply reply, String nickname, String args) {
		return send(reply.getCode(), nickname, args);
	}

	public boolean send(Reply reply, Client client, String args) {
		return send(reply.getCode(), client.getNickname(), args);
	}

	public boolean send(Error error, String nickname, String args) {
		return send(error.getCode(), nickname, args);
	}

	public boolean send(Error error, Client client, String args) {
		return send(error.getCode(), client.getNickname(), args);
	}

	public void close(Optional<String> message) {

// TODO unregister from dispacher
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Optional<String> getNickname() {
		if (client != null)
			return Optional.of(client.getNickname());
		return nickname;

	}

}
