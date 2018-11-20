package com.rbruno.irc.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

/**
 * Manages and gives access to the config and ops file.
 */
public class FileConfig implements Config {

    private int port;
    private String hostname;
    private Optional<String> adminLoc1;
    private Optional<String> adminLoc2;
    private Optional<String> adminMail;

    public FileConfig(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("#"))
                continue;

            switch (line.split("=")[0]) {
            case "port":
                this.port = Integer.parseInt(line.split("=")[1]);
                break;
            case "hostname":
                this.hostname = line.split("=")[1];
                break;
            case "adminLoc1":
                this.adminLoc1 = Optional.ofNullable(line.split("=")[1]);
                break;
            case "adminLoc2":
                this.adminLoc2 = Optional.ofNullable(line.split("=")[1]);
                break;
            case "adminMail":
                this.adminMail = Optional.ofNullable(line.split("=")[1]);
                break;
            }
        }

        bufferedReader.close();
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
    public Optional<String> getAdminLoc1() {
        return adminLoc1;
    }

    @Override
    public Optional<String> getAdminLoc2() {
        return adminLoc2;
    }

    @Override
    public Optional<String> getAdminMail() {
        return adminMail;
    }
}
