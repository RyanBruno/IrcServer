package com.rbruno.irc.net;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.rbruno.irc.events.NewRequestEvent;

public class IrcConnection {
    
    private SocketChannel socketChannel;
    private ByteBuffer incomingBuffer;

    public IrcConnection(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        incomingBuffer = ByteBuffer.allocate(512);        
    }

    public void read() {
        int read = socketChannel.read(incomingBuffer);

        if (incomingBuffer.asCharBuffer().toString().contains("\r\n")) {
            
        }

        Request request = new Request(socketChannel, readBuffer.asCharBuffer().toString());
        getEventDispacher().dispach(new NewRequestEvent(request));        
    }

}
