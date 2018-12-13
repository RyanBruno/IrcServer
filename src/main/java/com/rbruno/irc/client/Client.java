package com.rbruno.irc.client;

import io.netty.channel.Channel;

public interface Client {

    public Channel getChannel();

    public String getNickname();

    public String getUsername();

    public String getHostname();

    public String getServername();

    public String getRealName();

    public String getAbsoluteName();

    public String getAwayMessage();

    public boolean isMode(ClientMode mode);

    public Client setNickname(String nickname);

    public Client setAwayMessage(String message);

    public Client setModes(byte mode);

}
