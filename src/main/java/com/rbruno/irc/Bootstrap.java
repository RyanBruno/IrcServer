package com.rbruno.irc;

import java.io.IOException;
import java.util.logging.Level;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.command.ClientCommandInvoker;
import com.rbruno.irc.command.CommandInvoker;
import com.rbruno.irc.command.RegistrationCommandInvoker;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.config.FileConfig;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.oper.BasicOperManager;
import com.rbruno.irc.oper.OperManager;
import com.rbruno.irc.plugin.PluginManager;

public class Bootstrap {

	public Config createConfig() throws Exception {
		try {
			return new FileConfig("config.txt");
		} catch (Exception e) {
			Logger.log("There has been a fatal error while parsing the config.", Level.SEVERE);
			throw e;
		}
	}

	public ClientManager createClientManager() {
		return new ClientManager();
	}

	public ChannelManager createChannelManager() {
		try {
			return new ChannelManager();
		} catch (Exception e) {
			Logger.log("There has been a fatal error while parsing the channels file.", Level.SEVERE);
			throw e;
		}
	}

	public PluginManager createPluginManager() throws IOException {
		return null;
	}

	public CommandInvoker createCommandInvoker() {
		return null;
	}

	public OperManager createOperManager() {
		return new BasicOperManager();
	}

}
