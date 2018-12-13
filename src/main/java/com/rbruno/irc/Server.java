package com.rbruno.irc;

import com.rbruno.irc.bus.CommandBus;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.config.FileConfig;
import com.rbruno.irc.events.ConfigChangedEvent;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.net.NetworkingModule;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server {

    public static final String VERSION = "v1.0-RELEASE";

    private Config config;

    /**
     * Server constructor. Starts all managers, opens the socket and starts the
     * running thread.
     * 
     * @param bootStrap
     * 
     * @throws Exception
     */
    private Server(Config config) throws Exception {
        EventDispacher eventDispacher = new EventDispacher();
        CommandBus bus = new CommandBus();
        
        // TODO ADD all the modules
        
        //new RegCommandModule(eventDispacher);
        
        eventDispacher.dispach(new ConfigChangedEvent(config));
 
        new NetworkingModule(bus, config.getPort());
        
    }

    public static void main(String args[]) throws Exception {
        new Server(new FileConfig("config.txt"));
    }

    /**
     * Gets Config object.
     * 
     * @return Config.
     */
    public Config getConfig() {
        return config;
    }

}
