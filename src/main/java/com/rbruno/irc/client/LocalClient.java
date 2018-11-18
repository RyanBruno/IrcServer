package com.rbruno.irc.client;

import java.util.Optional;

import com.rbruno.irc.net.Connection;

/**
 * Stores information on a local client.
 */
public class LocalClient implements Client {

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

    public LocalClient(Connection connection, String nickname, String username, String hostname, String servername, String realName) {
        this.connection = connection;
        this.nickname = nickname;
        this.username = username;
        this.hostname = hostname;
        this.servername = servername;
        this.realName = realName;
    }

    /**
     * Returns the Client's nickname.
     * 
     * @return The Client's nickname.
     */
    public String getNickname() {
        return nickname;
    }
    
    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the Client's real name.
     * 
     * @return The Client's real name.
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Returns the Client's username.
     * 
     * @return The Client's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the hostname.
     * 
     * @return The hostname.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Return the server name.
     * 
     * @return The server name.
     */
    public String getServername() {
        return servername;
    }

    /**
     * Returns the last time the user has sent a message. In Unix time.
     * 
     * @return The last time the user has sent a message. In Unix time.
     */
    public long getLastCheckin() {
        return lastCheckin;
    }

    /**
     * Sets the last time a user has sent a message. In Unix time.
     * 
     * @param lastCheckin The last time a user has sent a message. In Unix time.
     */
    public void setLastCheckin(long lastCheckin) {
        this.lastCheckin = lastCheckin;
    }

    /**
     * Returns the hop count.
     * 
     * @return The hop count.
     */
    public int getHopCount() {
        return 0;
    }

    /**
     * Returns Nickname!Username@Hostname
     * 
     * @return Nickname!Username@Hostname
     */
    public String getAbsoluteName() {
        return this.getNickname() + "!" + this.getUsername() + "@" + this.getHostname();
    }

    public void setAwayMessage(Optional<String> message) {
        this.awayMessage = message;
    }

    public Optional<String> getAwayMessage() {
        return awayMessage;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
