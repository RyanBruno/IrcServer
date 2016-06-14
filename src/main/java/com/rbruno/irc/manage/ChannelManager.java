package com.rbruno.irc.manage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.util.Utilities;

public class ChannelManager {

	private ArrayList<Channel> channels = new ArrayList<Channel>();


	public ChannelManager() throws IOException {
		File channels = new File("channels.txt");
		if (!channels.exists()) Utilities.makeFile("channels.txt");
		Properties channelProps = new Properties();
		channelProps.load(new FileReader(channels));
		Iterator<Object> channelKeys = channelProps.keySet().iterator();
		while (channelKeys.hasNext()){
			String name = (String) channelKeys.next();
			this.channels.add(new Channel(name, channelProps.getProperty(name)));
		}
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
