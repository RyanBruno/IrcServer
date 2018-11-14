package com.rbruno.irc.client;

import java.util.ArrayList;

import com.rbruno.irc.channel.Channel;

/**
 * Stores information on a client local and remote.
 */
public class Client {

  private String nickname;
  private String username;
  private String hostname;
  private String servername;
  private String realName;

  private long lastCheckin;

  private ArrayList<Channel> channels = new ArrayList<Channel>();
  private ArrayList<Channel> invitedChannels = new ArrayList<Channel>();

  private ArrayList<Character> modes = new ArrayList<Character>();
  private String awayMessage = "";

  public Client(String nickname) {
    this.nickname = nickname;
  }

  /**
   * Returns the Client's nickname.
   * 
   * @return The Client's nickname.
   */
  public String getNickname() {
    return nickname;
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
   * Sets the Client's nickname.
   * 
   * @param nickname The Clients new nickname.
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * Sets the Client's real name.
   * 
   * @param realName The Client's new real name.
   */
  public void setRealName(String realName) {
    this.realName = realName;
  }

  /**
   * Sets the Client's user name.
   * 
   * @param username The Client's new user name.
   */
  public void setUsername(String username) {
    this.username = username;
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
   * Sets the hostname
   * 
   * @param hostname The Client's new hostname.
   */
  public void setHostname(String hostname) {
    this.hostname = hostname;
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
   * Sets the server name.
   * 
   * @param servername The Client's new server name.
   */
  public void setServername(String servername) {
    this.servername = servername;
  }

  /**
   * Returns the list of channels the Client has joined.
   * 
   * @return The list of channels the Client has joined.
   */
  public ArrayList<Channel> getChannels() {
    return channels;
  }

  /**
   * Adds a Channels.
   * 
   * @param channel Channels to add.
   */
  public void addChannel(Channel channel) {
    channels.add(channel);
  }

  /**
   * Removes a Channel.
   * 
   * @param channel Channels to remove.
   */
  public void removeChannel(Channel channel) {
    channels.remove(channel);
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

  /**
   * Sets away message. If away message is "" then the user is not away.
   * 
   * @param message The away message.
   */
  public void setAwayMessage(String message) {
    this.awayMessage = message;
  }

  /**
   * Returns away message. If away message is "" then the user is not away.
   * 
   * @return Away message.
   */
  public String getAwayMessage() {
    return awayMessage;
  }

  public void addInvite(Channel channel) {
    invitedChannels.add(channel);
  }

  public boolean isInvitedTo(Channel channel) {
    return invitedChannels.contains(channel);
  }

  public ArrayList<Character> getModes() {
    return modes;
  }

  public void setMode(char mode, boolean add) {
    if (add) {
      if (!modes.contains(mode))
        modes.add(mode);
    } else {
      modes.remove(mode);
    }

  }

}
