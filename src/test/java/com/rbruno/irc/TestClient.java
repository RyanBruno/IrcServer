package com.rbruno.irc;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.Connection;

public class TestClient implements Client {

    private String nickname;
    private String username;
    private String hostname;
    private String servername;
    private String realName;

    private long lastCheckin;
    private Optional<String> awayMessage = Optional.empty();

    public boolean invisible;
    public boolean notice;
    public boolean wallops;
    public boolean operator;

    private Connection connection;

    public TestClient(Connection connection, String nickname, String username, String hostname, String servername, String realName) {
        this.connection = connection;
        this.nickname = nickname;
        this.username = username;
        this.hostname = hostname;
        this.servername = servername;
        this.realName = realName;
    
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public void setNickname(String nickname) {

    }

    @Override
    public String getRealName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHostname() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastCheckin(long lastCheckin) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getAbsoluteName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAwayMessage(Optional<String> message) {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<String> getAwayMessage() {
        return null;
    }

}
