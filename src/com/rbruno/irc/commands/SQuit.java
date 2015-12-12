package com.rbruno.irc.commands;

import com.rbruno.irc.Request;

public class SQuit extends Command {

	public SQuit() {
		super("SQUIT", 0);
	}
	
	@Override
	public void execute(Request request) throws Exception {
		
	}

}
