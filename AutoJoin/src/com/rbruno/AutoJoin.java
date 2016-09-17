package com.rbruno;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.plugin.Plugin;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;
import com.rbruno.irc.util.Utilities;

public class AutoJoin extends Plugin {
	
	public ArrayList<String> autoJoinsChannels = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		Logger.log("[" + this.getName() + "] Plugin Enabled");
		if (new File("autojoinschannels.txt").exists()) {
			try {
				for (String line : Utilities.read("autojoinschannels.txt")) {
					autoJoinsChannels.add(line);
				}
			} catch (IOException e) {
				Logger.log("[" + this.getName() + "] An error occured reading autojoinschannels.txt", Level.FINE);
			}
		}
	}
	
	@Override
	public void onClientLogin(Client client) {
		for (String channel : autoJoinsChannels) {
			try {
				Command.runCommand(new Request(client.getConnection(), "JOIN " + channel));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
