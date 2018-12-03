package com.rbruno.irc.plugin;

import java.io.File;

import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.net.Request;

/**
 * The Plugin Object. While making a Plugin your main class MUST extend this
 * class.
 */
public class Plugin {

    private String name;
    private File configFolder;

    /**
     * Runs when the Plugin is loaded.
     */
    public void onEnable() {
    }

    /**
     * Runs when a client logs in.
     * 
     * @param client The Client that just logged in.
     */
    public void onClientLogin(LocalClient client) {
    }

    /**
     * Runs when a user sends a Request to this server.
     * 
     * @param request The Client's Request.
     */
    public void onRequest(Request request) {
    }

    /**
     * Sets the Plugin name;
     * 
     * @param name The new Plugin name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Plugin's name.
     * 
     * @return The Plugin's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the folder where all Plugin configs should be stored.
     * 
     * @return The folder where all Plugin configs should be stored.
     */
    public File getConfigFolder() {
        return configFolder == null ? new File("plugins/" + name + "/") : configFolder;
    }

}
