package com.rbruno;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;

import com.rbruno.irc.Server;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.net.Connection;
import com.rbruno.irc.plugin.Plugin;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;
import com.rbruno.irc.util.Utilities;

public class NickServ extends Plugin {

	public File usersFile;

	@Override
	public void onEnable() {
		Logger.log("[" + this.getName() + "] Plugin Enabled");
		usersFile = new File(getConfigFolder().getPath() + "/users.txt");
		try {
			if (!usersFile.exists()) usersFile.createNewFile();
		} catch (IOException e) {
			Logger.log("[" + this.getName() + "] An error occured creating users.txt. (Check your file permissions)", Level.FINE);
			e.printStackTrace();
		}
		Socket socket = new Socket();
		try {
			socket.close();
			Server.getServer().getClientManager().addClient(new Client(new Connection(socket), "NickServ", "NickServ", "localhost", Server.getServer().getConfig().getProperty("hostname"), "NickServ"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequest(Request request) {
		if (!request.getCommand().equalsIgnoreCase("privmsg") || request.getArgs().length < 2) return;
		if (!request.getArgs()[0].equalsIgnoreCase("NickServ")) return;

		request.setCancelled(true);
		HashMap<String, Method> commands = new HashMap<String, Method>();
		try {
			commands.put("identify", NickServ.class.getMethod("identify", Request.class));
			commands.put("register", NickServ.class.getMethod("register", Request.class));
			commands.put("release", NickServ.class.getMethod("release", Request.class));

			String command = request.getArgs()[1].split(" ")[0].toLowerCase();

			for (String current : commands.keySet())
				if (command.equals(current)) {
					commands.get(current).invoke(this, request);
					return;
				}

			request.getConnection().send("NickServ", "PRIVMSG", request.getClient().getNickname() + " :Unknown Command!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClientLogin(Client client) {
		if (client.getConnection().getSocket().isClosed()) return;
		try {
			if (isUserRegistered(client)) {
				client.getConnection().send("NickServ", "NOTICE", client.getNickname() + " :Your nick is registered try /PRIVMSG NickServ identify <Password>");
			} else {
				client.getConnection().send("NickServ", "NOTICE", client.getNickname() + " :Your nick is not registered try /PRIVMSG NickServ register <Password>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void identify(Request request) throws IOException {
		if (!isUserRegistered(request.getClient())) {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :Your nick is not registered try /PRIVMSG NickServ register <Password>");
			return;
		}
		if (checkPassword(request.getClient().getNickname(), request.getArgs()[1].split(" ")[1])) {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :You are now Identified as " + request.getClient().getNickname());
			//TODO: Add +i
		} else {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :Incorrect password!");
		}
	}

	public void register(Request request) throws IOException {
		if (request.getArgs()[1].split(" ").length < 2) {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :More args needed.");
			return;
		}
		if (isUserRegistered(request.getClient())) {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :Password Changed!");
		} else {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :User Created!");
		}
		setPassword(request.getClient().getNickname(), request.getArgs()[1].split(" ")[1]);
		identify(request);
	}

	public void release(Request request) throws IOException { 
		if (request.getArgs().length < 2) {
			request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :More args needed.");
			return;
		}
		//TODO: Check if user has +i
		removeUser(request.getClient());
		request.getConnection().send("NickServ", "NOTICE", request.getClient().getNickname() + " :Nickname released!");
	}

	private void setPassword(String nickname, String password) throws FileNotFoundException, UnsupportedEncodingException {
		HashMap<String, String> keyMap = getKeyMap();
		keyMap.put(nickname, password);
		save(keyMap);
	}

	private void removeUser(Client client) throws IOException {
		HashMap<String, String> keyMap = getKeyMap();
		keyMap.remove(client);
		save(keyMap);
	}	

	private void save(HashMap<String, String> keyMap) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(usersFile, "UTF-8");
		for (String nickname : keyMap.keySet())
			writer.println(nickname + "=" + keyMap.get(nickname));
		writer.close();
	}

	private boolean checkPassword(String nickname, String password) throws IOException {
		// TODO Password Hashing
		return password.equals(getKeyMap().get(nickname));
	}

	private boolean isUserRegistered(Client client) throws IOException {
		for (String string : getKeyMap().keySet())
			if (string.equals(client.getNickname())) return true;
		return false;
	}

	private HashMap<String, String> getKeyMap() {
		HashMap<String, String> keyMap = new HashMap<String, String>();

		try {
			for (String line : Utilities.read(usersFile.getPath()))
				if (line.contains("=")) keyMap.put(line.split("=")[0], line.split("=")[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyMap;
	}

}
