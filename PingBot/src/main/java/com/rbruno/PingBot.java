package com.rbruno;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;

import com.cedarsoftware.util.io.JsonReader;
import com.rbruno.irc.Server;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.net.Connection;
import com.rbruno.irc.plugin.Plugin;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class PingBot extends Plugin {

	public File groupsFile;

	@Override
	public void onEnable() {
		Logger.log("[" + this.getName() + "] Plugin Enabled");
		groupsFile = new File(getConfigFolder().getPath() + "/groups.txt");
		try {
			if (!groupsFile.exists()) groupsFile.createNewFile();
		} catch (IOException e) {
			Logger.log("[" + this.getName() + "] An error occured creating users.txt. (Check your file permissions)", Level.FINE);
			e.printStackTrace();
		}
		Socket socket = new Socket();
		try {
			socket.close();
			Server.getServer().getClientManager().addClient(new Client(new Connection(socket), "PingBot", "PingBot", "localhost", Server.getServer().getConfig().getProperty("hostname"), "PingBot"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequest(Request request) {
		if (!request.getCommand().equalsIgnoreCase("privmsg") || request.getArgs().length < 2) return;
		if (!request.getArgs()[0].equalsIgnoreCase("PingBot")) return;
		try {
			if (!request.getArgs()[1].contains(":")) {
				request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :Invalid format (<Group>:<Message>");
				return;
			}

			String group = request.getArgs()[1].split(":")[0];
			if (!isGroup(group)){
				request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :Group not found!");
				return;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isGroup(String group) throws IOException {
		String[] keyArray = (String[]) JsonReader.jsonToJava(readGroupsJson());
		
		for (String current : keyArray) 
			if (current.equals(group))
				return true;
		return false;
	}

	private String readGroupsJson() throws IOException {		
		FileReader reader = new FileReader(groupsFile);
		char[] cbuf = new char[(int) Files.size(groupsFile.toPath())];
		reader.read(cbuf);	
		reader.close();
		return new String(cbuf);
	}

}
