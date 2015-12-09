package com.rbruno.irc.net;

public class AdjacentServer {

	private Connection connection;
	private String name;
	private int hopcount;
	private String info;

	public AdjacentServer(Connection connection, String name, int hopcount, String info) {
		this.connection = connection;
		this.name = name;
		this.hopcount = hopcount;
		this.info = info;
	}

	public Connection getConnection() {
		return connection;
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
