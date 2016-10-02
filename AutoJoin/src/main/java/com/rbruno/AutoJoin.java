package com.rbruno;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.plugin.Plugin;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;
import com.rbruno.irc.util.Utilities;

public class AutoJoin extends Plugin {

	@Override
	public void onEnable() {
		Logger.log("[" + this.getName() + "] Plugin Enabled");
		try {
			if (!new File(getConfigFolder().getPath() + "/channels.txt").exists())
				new File(getConfigFolder().getPath() + "/channels.txt").createNewFile();
		} catch (IOException e) {
			Logger.log("[" + this.getName() + "] An error occured creating channels.txt. (Check your file permissions)", Level.FINE);
			e.printStackTrace();
		}
	}

	@Override
	public void onClientLogin(Client client) {
		try {
			for (String line : Utilities.read(new File(getConfigFolder().getPath()) + "/channels.txt")) 
				Command.runCommand(new Request(client.getConnection(), "JOIN " + line));	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
