package com.rbruno.irc;

import java.util.ArrayList;

public class ChannelManager {
	
	private ArrayList<Channel> channels = new ArrayList<Channel>();

	public ArrayList<Channel> getChannels() {
		return channels;
	}

	public void addChannel(Channel channel) {
		channels.add(channel);
	}
	
	public Channel getChannel(String name) {
		for (Channel channel : channels) {
			if (channel.getName().equals(name)) return channel;
		}
		return null;
	}
	

}
