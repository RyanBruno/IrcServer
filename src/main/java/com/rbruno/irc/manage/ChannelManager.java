package com.rbruno.irc.manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Channel.ChannelMode;
import com.rbruno.irc.util.Utilities;

public class ChannelManager {

	private ArrayList<Channel> channels = new ArrayList<Channel>();


	public ChannelManager() throws IOException {
		File channels = new File("channels.txt");
		if (!channels.exists()) Utilities.makeFile("channels.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(channels));
		while (reader.ready()) {
			String line = reader.readLine();
			if (line.startsWith("//")) continue;
			String[] lineArray = line.split(":");
			Channel channel = new Channel(lineArray[0], lineArray[1]);
			channel.setUserLimit(Integer.parseInt(lineArray[2]));
			channel.setTopic(lineArray[4]);
			this.channels.add(channel);
		}
		reader.close();
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

	public int getNonSecretChannels() {
		int channels = 0;
		for (Channel channel : this.channels) 
			if (!channel.getMode(ChannelMode.SECRET))
				channels++;
		return channels;
	}

}
