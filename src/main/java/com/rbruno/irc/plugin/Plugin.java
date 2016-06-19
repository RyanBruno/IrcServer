package com.rbruno.irc.plugin;

import com.rbruno.irc.templates.Request;

public class Plugin {

	public Plugin(String name) {}
	
	public void onEnable() {}

	public void onDisable() {}
	
	public boolean onRequest(Request request) {return true;}
	
}
