package com.rbruno.irc.events;

import com.rbruno.irc.events.EventDispacher.EventType;
import com.rbruno.irc.net.Connection;

public class NickSetEvent extends Event {

	private Connection connection;
	private String nickname;

	public NickSetEvent(Connection connection, String nickname) {
		this.connection = connection;
		this.nickname = nickname;
	}

	@Override
	protected EventType getType() {
		return EventType.NICK_SET;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getNickname() {
		return nickname;
	}

}
