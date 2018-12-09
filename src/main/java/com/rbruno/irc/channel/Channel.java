package com.rbruno.irc.channel;

public interface Channel {

	public String getName();

	public String getTopic();

	public boolean hasClient(String nickname);
	
	public String getPassword();
	
	public boolean isMode(ChannelMode mode);

	public Channel addClient(String nickname);

	public Channel removeClient(String nickname);

	public Channel setTopic(String topic);
	
	public Channel setPassword(String password);
	
	public Channel setMode(int code);

}
