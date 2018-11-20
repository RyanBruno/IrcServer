package com.rbruno.irc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.command.ClientCommandInvoker;
import com.rbruno.irc.command.RegistrationCommandInvoker;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.net.Connection;
import com.rbruno.irc.oper.OperManager;
import com.rbruno.irc.plugin.PluginManager;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server {

    public static final String VERSION = "v1.0-RELEASE";

    private ServerSocket serverSocket;

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
    public Server(ServerBootStrap bootStrap) throws Exception {
        server = this;
        config = bootStrap.createConfig();
        clientManager = bootStrap.createClientManager();
        channelManger = bootStrap.createChannelManager();
        pluginManager = bootStrap.createPluginManager();
        regCommandInvoker = bootStrap.createRegCommandInvoker();
        clientCommandInvoker = bootStrap.createClientCommandInvoker();
        operManager = bootStrap.createOperManager();
        
        serverSocket = new ServerSocket(config.getPort());
        run();

        Logger.log("Started Server on port: " + serverSocket.getLocalPort());
    }

    /**
     * Main running thread. Waits for sockets then creates a new Connection object
     * on a new thread.
     */
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread connection = new Thread(new Connection(socket, regCommandInvoker));
                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        ServerBootStrap bootStrap = new IrcBootstrap();
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
    
    public static Server getServer() {
        return server;
    }

}
