package com.rbruno.irc;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.command.CommandInvoker;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.NetworkingModule;
import com.rbruno.irc.oper.OperManager;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server {

    public static final String VERSION = "v1.0-RELEASE";

    private Config config;
    private ClientManager clientManager;
    private ChannelManager channelManger;
    private CommandInvoker commandInvoker;
    private OperManager operManager;

    /**
     * Server constructor. Starts all managers, opens the socket and starts the
     * running thread.
     * 
     * @param bootStrap
     * 
     * @throws Exception
     */
    public Server(Bootstrap bootStrap) throws Exception {

        config = bootStrap.createConfig();
        clientManager = bootStrap.createClientManager();
        channelManger = bootStrap.createChannelManager();
        commandInvoker = bootStrap.createCommandInvoker();
        operManager = bootStrap.createOperManager();

        new NetworkingModule(config.getPort(), commandInvoker);
    }

    public static void main(String args[]) throws Exception {
        Bootstrap bootStrap = new Bootstrap();
        new Server(bootStrap);
    }

    public Config getConfig() {
        return config;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public ChannelManager getChannelManger() {
        return channelManger;
    }

    public OperManager getOperManager() {
        return operManager;
    }

}
