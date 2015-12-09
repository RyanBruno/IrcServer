package com.rbruno.irc.commands;

import com.rbruno.irc.Request;

public class Pass extends Command {

	/**
	 * The PASS command is used to set a 'connection password'.
	 * <p>
	 * Parameters: password
	 * <p>
	 * Replies: ERR_NEEDMOREPARAMS, ERR_ALREADYREGISTRED
	 * <p>
	 * Example: PASS secretpasswordhere
	 */
	public Pass() {
		super("PASS", 1);
	}

	@Override
	public void execute(Request request) {
		request.getConnection().setConnectionPassword(request.getArgs()[0]);		
	}

}
