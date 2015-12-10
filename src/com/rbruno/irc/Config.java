package com.rbruno.irc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private Properties config;
	private Properties ops;

	public Config() throws Exception {
		config = new Properties();
		config.load(new FileReader(new File("config.txt")));
		ops = new Properties();
		ops.load(new FileReader(new File("ops.txt")));
	}
	
	public String getProperty(String key) {
		return config.getProperty(key);
	}
	
	public void setProperty(String key, String value) throws IOException {
		config.setProperty(key, value);
		save();
	}
	
	public boolean checkOpPassword(String username, String password) {
		return password.equals(ops.get(username));
	}
	
	public void addOp(String username, String password) throws IOException {
		ops.setProperty(username, password);
		save();
	}
	
	private void save() throws IOException {
		config.store(new FileWriter(new File("config.txt")), "RBruno's server config.");
		ops.store(new FileWriter(new File("ops.txt")), "RBruno's op list.");
	}
}
