package com.rbruno.irc.manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Channel.ChannelMode;
import com.rbruno.irc.util.Utilities;

/**
 * Manages all channels. Also adds channels from channels.txt file.
 */
public class ChannelManager {

	private ArrayList<Channel> channels = new ArrayList<Channel>();

	/**
	 * Creates a new ClientManager object. Reads and process channels.txt. If
	 * channels.txt does not exist it will create one.
	 * 
	 * @throws IOException
	 */
	public ChannelManager() throws IOException {
		File channels = new File("channels.txt");
		if (!channels.exists()) Utilities.makeFile("channels.txt");

		BufferedReader reader = new BufferedReader(new FileReader(channels));
		while (reader.ready()) {
			String line = reader.readLine();
			if (line.startsWith("//")) continue;
			String[] lineArray = line.split(":");
			if (lineArray.length < 5) continue;
			Channel channel = new Channel(lineArray[0], lineArray[1]);
			channel.setUserLimit(Integer.parseInt(lineArray[2]));
			for (char c : lineArray[3].toCharArray()) {
				switch (c) {
				case 'p':
					channel.setMode(ChannelMode.PRIVATE, true);
					break;
				case 's':
					channel.setMode(ChannelMode.SECRET, true);
					break;
				case 'i':
					channel.setMode(ChannelMode.INVITE_ONLY, true);
					break;
				case 't':
					channel.setMode(ChannelMode.TOPIC, true);
					break;
				case 'n':
					channel.setMode(ChannelMode.NO_MESSAGE_BY_OUTSIDE, true);
					break;
				case 'm':
					channel.setMode(ChannelMode.MODERATED_CHANNEL, true);
					break;
				}
			}
			channel.setTopic(lineArray[4]);
			this.channels.add(channel);
		}
		reader.close();
	}

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
	 * @param channel
	 *            Channel to be added.
	 */
	public void addChannel(Channel channel) {
		channels.add(channel);
	}

	/**
	 * Returns channel with the given name.
	 * 
	 * @param name
	 *            Name of channel that is returned.
	 * @return Returns Channel with the given name or null if channel does not
	 *         exist.
	 */
	public Channel getChannel(String name) {
		for (Channel channel : this.getChannels())
			if (channel.getName().equals(name)) return channel;
		return null;
	}

	/**
	 * Returns the number of channels that do not have the secret flag.
	 * 
	 * @return The number of channels that do not have the secret flag.
	 */
	public int getNonSecretChannels() {
		int channels = 0;
		for (Channel channel : this.getChannels())
			if (!channel.getMode(ChannelMode.SECRET)) channels++;
		return channels;
	}

}
