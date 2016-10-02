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

	@Override
	public void onEnable() {
		Logger.log("[" + this.getName() + "] Plugin Enabled");
		try {
			if (!new File(getConfigFolder().getPath() + "/users.txt").exists())
				new File(getConfigFolder().getPath() + "/users.txt").createNewFile();
		} catch (IOException e) {
			Logger.log("[" + this.getName() + "] An error occured creating users.txt. (Check your file permissions)", Level.FINE);
			e.printStackTrace();
		}
	}

	@Override
	public void onClientLogin(Client client) {
		try {
			HashMap<String, String> keyMap = new HashMap<String, String>();

			for (String line : Utilities.read(new File(getConfigFolder().getPath()) + "/users.txt"))
				if (line.contains("="))
					keyMap.put(line.split("=")[0], line.split("=")[1]);

			if (keyMap.containsKey(client.getNickname())) {
				if (!keyMap.get(client.getNickname()).equals(client.getConnection().getConnectionPassword()))
					try {
						client.getConnection().send(Error.ERR_ALREADYREGISTRED, client, ":You may not reregister");
						client.getConnection().close();
						Server.getServer().getClientManager().removeClient(client);
					} catch (Exception e) {
						e.printStackTrace();
					}
			} else {
				if (!client.getConnection().getConnectionPassword().equals(""))
					setPassword(client.getNickname(), client.getConnection().getConnectionPassword());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setPassword(String nickname, String connectionPassword) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(getConfigFolder().getPath() + "/users.txt", true)));
			out.println(nickname + "=" + connectionPassword);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
