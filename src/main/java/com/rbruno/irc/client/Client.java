package com.rbruno.irc.client;

import java.util.Optional;

import com.rbruno.irc.net.Connection;

public interface Client {

    public Connection getConnection();

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
