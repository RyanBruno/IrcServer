package com.rbruno.irc.net;

public class AdjacentServer extends PasswordUser {

	private String name;
	private int hopcount;
	private String info;

	public AdjacentServer(String password, String name, int hopcount, String info) {
		super(password);
		this.name = name;
		this.hopcount = hopcount;
		this.info = info;
	}

	public AdjacentServer(PasswordUser connection, String name, int hopcount, String info) {
		super(connection.getPassword());
		this.name = name;
		this.hopcount = hopcount;
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public int getHopcount() {
		return hopcount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setHopcount(int hopcount) {
		this.hopcount = hopcount;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
