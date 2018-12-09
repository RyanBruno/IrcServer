package com.rbruno.irc.client;

import java.nio.channels.SocketChannel;

/**
 * Stores information on a local client.
 */
public class LocalClient implements Client {

    private SocketChannel socketChannel;

    private String username;
    private String hostname;
    private String servername;
    private String realName;

    private String nickname;
    private String awayMessage;

    private int modeMap = ClientMode.NOTICE.code;

    public enum ClientMode {
        INVISIBLE(1), NOTICE(2), WALLOPS(4), OPERATOR(8);
        public final int code;

        private ClientMode(int code) {
            this.code = code;
        }
    }

    public boolean invisible;
    public boolean notice;
    public boolean wallops;
    public boolean operator;

    public LocalClient(SocketChannel socketChannel, String nickname, String username, String hostname, String servername, String realName) {
        this.socketChannel = socketChannel;
        this.nickname = nickname;
        this.username = username;
        this.hostname = hostname;
        this.servername = servername;
        this.realName = realName;
    }

    public LocalClient(SocketChannel socketChannel, String nickname, String username, String hostname, String servername, String realName, String awayMessage, int modeMap) {
        this(socketChannel, nickname, username, hostname, servername, realName);
        this.awayMessage = awayMessage;
        this.modeMap = modeMap;
    }

    @Override
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public String getServername() {
        return servername;
    }

    @Override
    public String getRealName() {
        return realName;
    }

    public String getAbsoluteName() {
        return this.getNickname() + "!" + this.getUsername() + "@" + this.getHostname();
    }

    @Override
    public String getAwayMessage() {
        return awayMessage;
    }

    @Override
    public boolean isMode(ClientMode mode) {
        return (modeMap & mode.code) != 0;
    }

    @Override
    public Client setNickname(String nickname) {
        return new LocalClient(socketChannel, nickname, username, hostname, servername, realName , awayMessage, modeMap);
    }

    @Override
    public Client setAwayMessage(String awayMessage) {
        return new LocalClient(socketChannel, nickname, username, hostname, servername, realName , awayMessage, modeMap);
    }

    @Override
    public Client setModes(byte modeMap) {
        return new LocalClient(socketChannel, nickname, username, hostname, servername, realName , awayMessage, modeMap);
    }
}
