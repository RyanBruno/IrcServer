package com.rbruno.irc.client;

import java.util.Optional;

import io.netty.channel.Channel;

public interface Client {

	public Channel getChannel();

	public String getNickname();

	public void setNickname(String nickname);

	public String getRealName();

	public String getUsername();

	public String getHostname();

	public String getServername();

	public void setLastCheckin(long lastCheckin);

	public String getAbsoluteName();

	public void setAwayMessage(Optional<String> message);

	public Optional<String> getAwayMessage();

}
