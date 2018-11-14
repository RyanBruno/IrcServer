package com.rbruno.irc;

import java.io.IOException;
import java.util.logging.Level;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.command.ClientCommandInvoker;
import com.rbruno.irc.command.CommandInvoker;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.config.FileConfig;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.plugin.PluginManager;

public class IrcBootstrap implements ServerBootStrap {

  @Override
  public Config createConfig() throws Exception {
    try {
      return new FileConfig("config.txt");
    } catch (Exception e) {
      Logger.log("There has been a fatal error while parsing the config.", Level.SEVERE);
      throw e;
    }
  }
  

  @Override
  public ClientManager createClientManager() {
    return new ClientManager();
  }
  
  @Override
  public ChannelManager createChannelManager() {
    try {
      return new ChannelManager();
    } catch (Exception e) {
      Logger.log("There has been a fatal error while parsing the channels file.", Level.SEVERE);
      throw e;
    }
  }

  @Override
  public PluginManager createPluginManager() throws IOException {
    try {
      return new PluginManager();
    } catch (IOException e) {
      Logger.log("There has been a fatal error while reading the plugins folder. Check your permissions.", Level.SEVERE);
      throw e;
    }
  }


  @Override
  public CommandInvoker createCommandInvoker() {
    return new ClientCommandInvoker();
  }

}
