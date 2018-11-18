package com.rbruno.irc.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Manages and gives access to the config and ops file.
 */
public class FileConfig extends StreamConfig implements Config {

    /**
     * Creates a new Config object. Loads from ops.txt and config.txt to be read. If
     * ops.txt and config.txt do not exist will it create them.
     * 
     * @throws IOException
     * 
     */
    public FileConfig(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        super.load(bufferedReader.lines());
        bufferedReader.close();
    }
}
