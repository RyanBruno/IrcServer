package com.rbruno.irc.net;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

import io.netty.channel.Channel;;

public class Response {

	private Channel channel;
	private String message;

	public Response(Channel channel, String message) {
		this.channel = channel;
		this.message = message;
	}

	public Response(Client client, String message) {
		this(client.getChannel(), message);
	}

	public Response(Client client, String prefix, String command, String args) {
		this(client, ":" + prefix + " " + command + " " + args);
	}

	public Response(int code, Client client, String args, String hostname) {
		String stringCode = code + "";
		if (stringCode.length() < 2)
			stringCode = "0" + stringCode;
		if (stringCode.length() < 3)
			stringCode = "0" + stringCode;

		this.channel = client.getChannel();
		this.message = ":" + hostname + " " + stringCode + " " + client.getNickname() + " " + args;
	}

	public Response(Reply reply, Client client, String args, String hostname) {
		this(reply.getCode(), client, args, hostname);
	}

	public Response(Error error, Client client, String args, String hostname) {
		this(error.getCode(), client, args, hostname);
	}
	
	
	/**
	 * Special constructor for unknowncommand. TODO put in separate class.
	 * @param channel
	 * @param command
	 * @param hostname
	 */
	public Response(Channel channel, String command, String hostname) {
		this(channel, ":" + hostname + " " + Error.ERR_UNKNOWNCOMMAND.getCode() + " " + command + " :Unknown Command");
	}

	public Channel getChannel() {
		return channel;
	}

	public String getMessage() {
		return message;
	}
}
