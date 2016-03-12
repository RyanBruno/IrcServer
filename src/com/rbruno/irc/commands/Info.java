package com.rbruno.irc.commands;

import java.io.IOException;
import java.io.InputStream;

import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;

public class Info extends Command {

	public Info() {
		super("INFO", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0) {
			for (String current : read("/config.txt").split("\n"))
				request.getConnection().send(Reply.RPL_INFO, request.getClient(), current);
			request.getConnection().send(Reply.RPL_ENDOFINFO, request.getClient(), ":End of /INFO list");
		} else {
			// TODO: Server
		}
	}

	private String read(String fileName) throws IOException {
		InputStream inputStream = Info.class.getResourceAsStream("/config.txt");
		byte[] buffer = new byte[100];
		inputStream.read(buffer);
		return new String(buffer);
	}

}
