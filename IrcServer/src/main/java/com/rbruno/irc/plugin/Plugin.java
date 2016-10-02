package com.rbruno.irc.plugin;

import java.io.File;

import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Plugin {

	private String name;
	private File configFolder;

	public void onEnable() {
	}

	public void onClientLogin(Client client) {
	}

	public void onRequest(Request request) {
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public File getConfigFolder() {
		return configFolder == null ? new File("plugins/" + name + "/") : configFolder;
	}

}
