package com.rbruno.irc.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Manages and gives access to the config and ops file.
 */
public class FileConfig implements Config {

	private int port;
	private String hostname;
	private String adminLoc1;
	private String adminLoc2;
	private String adminMail;

	public FileConfig(String fileName) throws Exception {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			bufferedReader = new BufferedReader(new InputStreamReader(FileConfig.class.getResourceAsStream("/config.txt")));
		}

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith("#"))
				continue;

			switch (line.split("=")[0].toLowerCase()) {
			case "port":
				this.port = Integer.parseInt(line.split("=")[1]);
				break;
			case "hostname":
				this.hostname = line.split("=")[1];
				break;
			case "adminname":
				this.adminLoc1 = line.split("=")[1];
				break;
			case "adminnick":
				this.adminLoc2 = line.split("=")[1];
				break;
			case "adminemail":
				this.adminMail = line.split("=")[1];
				break;
			}
		}
		bufferedReader.close();

		if (port == 0 || hostname == null) {
			throw new Exception("Port or hostname not found in config");
		}

		if (adminLoc1 == null || adminLoc2 == null || adminMail == null) {
			throw new Exception("Admin Info not found in config");
		}

	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public String getAdminLoc1() {
		return adminLoc1;
	}

	@Override
	public String getAdminLoc2() {
		return adminLoc2;
	}

	@Override
	public String getAdminMail() {
		return adminMail;
	}
}
