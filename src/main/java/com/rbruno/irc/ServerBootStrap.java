package com.rbruno.irc;

import java.io.IOException;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.command.CommandInvoker;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.oper.OperManager;
import com.rbruno.irc.plugin.PluginManager;

public interface ServerBootStrap {

    public Config createConfig() throws Exception;

    public ClientManager createClientManager();

    public ChannelManager createChannelManager();

    public PluginManager createPluginManager() throws IOException;

    public CommandInvoker createCommandInvoker();

    public OperManager createOperManager();

}
