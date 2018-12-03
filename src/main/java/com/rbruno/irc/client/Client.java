package com.rbruno.irc.client;

import java.nio.channels.SocketChannel;
import java.util.Optional;

public interface Client {

    public SocketChannel getSocketChannel();

    public String getNickname();

    public void setNickname(String nickname);

    public String getRealName();

    public String getUsername();

    public String getHostname();

    public String getServername();

    public void setLastCheckIn(long lastCheckin);

    public String getAbsoluteName();

    public void setAwayMessage(Optional<String> message);

    public Optional<String> getAwayMessage();

}
