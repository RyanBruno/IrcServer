package com.rbruno.irc.channel;

import java.util.ArrayList;

/**
 * Manages all channels. Also adds channels from channels.txt file.
 */
public class ChannelManager {

    private ArrayList<Channel> channels = new ArrayList<Channel>();

    /**
     * Returns an ArrayList of all the channels.
     * 
     * @return an ArrayList of all the channels.
     */
    public ArrayList<Channel> getChannels() {
        return channels;
    }

    /**
     * Adds a channel to the Array.
     * 
     * @param channel Channel to be added.
     */
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    /**
     * Removes a channel from the Array.
     * 
     * @param channel Channel to be removed.
     */
    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    /**
     * Returns channel with the given name.
     * 
     * @param name Name of channel that is returned.
     * @return Returns Channel with the given name or null if channel does not
     *         exist.
     */
    public Channel getChannel(String name) {
        for (Channel channel : this.getChannels())
            if (channel.getName().equals(name))
                return channel;
        return null;
    }

}
