package com.rbruno.irc;

import java.util.ArrayList;
import java.util.Optional;

import com.rbruno.irc.net.Connection;

public class TestConnection extends Connection {

    public Optional<String> closed = Optional.empty();
    
    public ArrayList<String> sentLines = new ArrayList<String>();

    public TestConnection() {
        super(null, null);
    }

    @Override
    public void run() {
    }

    @Override
    public boolean send(byte[] block) {
        sentLines.add(new String(block));
        return true;
    }

    @Override
    public boolean send(String message) {
        return send(message.concat("\r\n").getBytes());
    }

    @Override
    public void close(Optional<String> message) {
        closed = message;
    }
}
