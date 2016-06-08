package com.rbruno.irc.config;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Config {

	private Properties config;
	private Properties ops;

	public Config() throws Exception {
		File config = new File("config.txt");
		File ops = new File("ops.txt");
		if(!config.exists()){
			makeFile("config.txt");
		}
		if(!ops.exists()){
			makeFile("ops.txt");
		}
		this.config = new Properties();
		this.config.load(new FileReader(config));
		this.ops = new Properties();
		this.ops.load(new FileReader(ops));
	}
	
	private void makeFile(String fileName) throws IOException {
		InputStream inputStream = Config.class.getResourceAsStream("/" + fileName);
		Files.copy(inputStream, new File(fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
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
