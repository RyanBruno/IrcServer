package com.rbruno.irc;

import com.rbruno.irc.config.Config;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.ServerOpenEvent;
import com.rbruno.irc.net.NetworkingModule;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server {

    public static final String VERSION = "v1.0-RELEASE";

    private Config config;

    private static Server server;

    /**
     * Server constructor. Starts all managers, opens the socket and starts the
     * running thread.
     * 
     * @param bootStrap
     * 
     * @throws Exception
     */
    public Server() throws Exception {
        server = this;
        EventDispacher eventDispacher = new EventDispacher();
        
        // TODO ADD all the modules
        
        new NetworkingModule(eventDispacher);
        eventDispacher.dispach(new ServerOpenEvent());
    }

    public static void main(String args[]) throws Exception {
        new Server();
    }

    /**
     * Gets Config object.
     * 
     * @return Config.
     */
    public Config getConfig() {
        return config;
    }

    public static Server getServer() {
        return server;
    }

}
