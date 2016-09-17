package com.rbruno;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;

import com.rbruno.irc.Server;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.plugin.Plugin;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.util.Utilities;

public class AuthPlugin extends Plugin {

	HashMap<String, String> nicksAndPassword = new HashMap<String, String>();

	@Override
	public void onEnable() {
		Logger.log("[" + this.getName() + "] Plugin Enabled");
		if (new File("users.txt").exists()) {
			try {
				for (String line : Utilities.read("users.txt")) {
					if (line.split("=").length > 1) {
						nicksAndPassword.put(line.split("=")[0], line.split("=")[1]);
					}
				}
			} catch (IOException e) {
				Logger.log("[" + this.getName() + "] An error occured reading users.txt", Level.FINE);
			}
		}
	}

	@Override
	public void onClientLogin(Client client) {
		if (nicksAndPassword.containsKey(client.getNickname())) {
			if (!nicksAndPassword.get(client.getNickname()).equals(client.getConnection().getConnectionPassword())) {
				try {
					client.getConnection().send(Error.ERR_ALREADYREGISTRED, client, ":You may not reregister");
				} catch (Exception e) {
				}
				client.getConnection().close();
				Server.getServer().getClientManager().removeClient(client);
			}
		} else {
			if (!client.getConnection().getConnectionPassword().equals("")) {
				setPassword(client.getNickname(), client.getConnection().getConnectionPassword());

			}
		}
	}

	private void setPassword(String nickname, String connectionPassword) {
		try {
			nicksAndPassword.put(nickname, connectionPassword);
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", true)));
			out.println(nickname + "=" + connectionPassword);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
