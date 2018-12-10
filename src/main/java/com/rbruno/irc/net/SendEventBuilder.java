package com.rbruno.irc.net;

import java.util.ArrayList;
import java.util.List;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.SendDataEvent;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class SendEventBuilder {
	
	private String hostname;
	private List<byte[]> messages;

	public SendEventBuilder(String hostname) {
		this.hostname = hostname;
		messages = new ArrayList<byte[]>();
	}
	
	public boolean addMessage(byte[] data) {
		return messages.add(data);
	}

	public boolean addMessage(String message) {
		return addMessage(message.concat("\r\n").getBytes());
	}

	public boolean addMessage(String prefix, String command, String args) {
		String message = ":" + prefix + " " + command + " " + args;
		return addMessage(message);
	}

	public boolean addMessage(int code, String nickname, String args) {
		String stringCode = code + "";
		if (stringCode.length() < 2)
			stringCode = "0" + stringCode;
		if (stringCode.length() < 3)
			stringCode = "0" + stringCode;

		String message = ":" + hostname + " " + stringCode + " " + nickname + " " + args;
		return addMessage(message);
	}

	public boolean addMessage(Reply reply, String nickname, String args) {
		return addMessage(reply.getCode(), nickname, args);
	}

	public boolean addMessage(Reply reply, Client client, String args) {
		return addMessage(reply.getCode(), client.getNickname(), args);
	}

	public boolean addMessage(Error error, String nickname, String args) {
		return addMessage(error.getCode(), nickname, args);
	}

	public boolean addMessage(Error error, Client client, String args) {
		return addMessage(error.getCode(), client.getNickname(), args);
	}

	public SendDataEvent getEvent() {

		return null;
	}
}
