package com.rbruno.irc.channel;

import org.apache.commons.lang3.ArrayUtils;

public class LocalChannel implements Channel {

    private String name;
    private String topic;
    private String password;
    
    private int limit;
    
    private int modeMap;

    private String[] clients;

    public LocalChannel(String name, String topic, String[] clients) {
        this.name = name;
        this.topic = topic;

        this.clients = clients;
    }
    
    public LocalChannel(String name, String topic, int limit, int modeMap, String[] clients) {
        this.name = name;
        this.topic = topic;

        this.clients = clients;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Channel addClient(String nickname) {
        return new LocalChannel(getName(), getTopic(), limit, modeMap, ArrayUtils.add(clients, nickname));
    }

    @Override
    public Channel removeClient(String nickname) {
        int index = ArrayUtils.indexOf(clients, nickname);

        if (index < 0) {
        	return this;
        }
        
        return new LocalChannel(getName(), getTopic(), limit, modeMap, ArrayUtils.remove(clients, index));
    }

    @Override
    public Channel setTopic(String topic) {
        return new LocalChannel(getName(), topic, clients);
    }

    @Override
    public boolean hasClient(String nickname) {
        return ArrayUtils.contains(clients, nickname);
    }

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isMode(ChannelMode mode) {
		return (modeMap & mode.code) == 0;
	}

	@Override
	public Channel setPassword(String password) {
		return new LocalChannel(name, password, limit, modeMap, clients);
	}

	@Override
	public Channel setMode(int code) {
		return new LocalChannel(name, topic, limit, code, clients);
	}

}
