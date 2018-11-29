package com.rbruno.irc;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.command.ClientCommandInvoker;
import com.rbruno.irc.command.RegistrationCommandInvoker;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.oper.OperManager;
import com.rbruno.irc.plugin.PluginManager;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server {

    public static final String VERSION = "v1.0-RELEASE";

    private Config config;
    private ClientManager clientManager;
    private ChannelManager channelManger;
    private PluginManager pluginManager;
    private RegistrationCommandInvoker regCommandInvoker;
    private ClientCommandInvoker clientCommandInvoker;
    private OperManager operManager;

    private static Server server;

    /**
     * Server constructor. Starts all managers, opens the socket and starts the
     * running thread.
     * 
     * @param bootStrap
     * 
     * @throws Exception
     */
    public Server(Bootstrap bootStrap) throws Exception {
        server = this;

        config = bootStrap.createConfig();
        clientManager = bootStrap.createClientManager();
        channelManger = bootStrap.createChannelManager();
        pluginManager = bootStrap.createPluginManager();
        regCommandInvoker = bootStrap.createRegCommandInvoker();
        clientCommandInvoker = bootStrap.createClientCommandInvoker();
        operManager = bootStrap.createOperManager();

        bootStrap.createNetworking();
    }

    public static void main(String args[]) throws Exception {
        Bootstrap bootStrap = new Bootstrap();
        new Server(bootStrap);
    }

    /**
     * Gets Config object.
     * 
     * @return Config.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Returns the ClientManager.
     * 
     * @return ClientManager.
     */
    public ClientManager getClientManager() {
        return clientManager;
    }

    /**
     * Returns the ChannelManager.
     * 
     * @return ChannelManager.
     */
    public ChannelManager getChannelManger() {
        return channelManger;
    }

    /**
     * Returns the PluginManager
     * 
     * @return PluginManager
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public ClientCommandInvoker getClientCommandInvoker() {
        return clientCommandInvoker;
    }

    public OperManager getOperManager() {
        return operManager;
    }

    public RegistrationCommandInvoker getRegCommandInvoker() {
        return regCommandInvoker;
    }

    public static Server getServer() {
        return server;
    }

}
