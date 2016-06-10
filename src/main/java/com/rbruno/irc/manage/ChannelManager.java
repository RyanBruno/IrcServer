package com.rbruno.irc.manage;

import java.util.ArrayList;

import com.rbruno.irc.templates.Channel;

public class ChannelManager {

	private ArrayList<Channel> channels = new ArrayList<Channel>();
	
	public ChannelManager() {
		channels.add(new Channel("#test", ""));
	}

	public ArrayList<Channel> getChannels() {
		return channels;
	}

	public void addChannel(Channel channel) {
		channels.add(channel);
	}

	public Channel getChannel(String name) {
		for (Channel channel : channels) {
			if (channel.getName().equals(name))
				return channel;
		}
		return null;
	}

}
