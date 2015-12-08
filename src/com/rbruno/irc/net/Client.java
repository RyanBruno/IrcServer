package com.rbruno.irc.net;


public class Client extends PasswordUser {

	private String nickname;
	private String realName;
	private String username;

	public Client(String password, String nickname, String realName, String username) {
		super(password);
		this.nickname = nickname;
		this.realName = realName;
		this.username = username;
	}

	public Client(PasswordUser connection, String nickname, String realName, String username) {
		super(connection.getPassword());
		this.nickname = nickname;
		this.realName = realName;
		this.username = username;
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

}
