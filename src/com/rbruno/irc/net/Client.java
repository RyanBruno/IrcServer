package com.rbruno.irc.net;

public class Client {

	private Connection connection;
	private String nickname;
	private String username;
	private String hostname;
	private String servername;
	private String realName;

	public Client(Connection connection, String nickname) {
		this.connection = connection;
		this.nickname = nickname;
	}

	public Client(Connection connection, String nickname, String username, String hostname, String servername, String realName) {
		this.connection = connection;
		this.nickname = nickname;
		this.username = username;
		this.hostname = hostname;
		this.servername = servername;
		this.realName = realName;

	}

	public String getNickname() {
		return nickname;
	}

	public String getRealName() {
		return realName;
	}

	public String getUsername() {
		return username;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}
}
