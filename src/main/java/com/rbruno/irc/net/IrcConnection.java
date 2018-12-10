package com.rbruno.irc.net;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

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
        
        incomingBuffer.flip();
        
        int index;
        while (index = findRequestIndex(incomingBuffer) != -1) {
            
        }
        // While (Check for \r\n
        // Read request for 

    }
    
    public String findRequest(ByteBuffer buffer) {
        int before = incomingBuffer.position();
        List<Character> request = new ArrayList<Character>();
        while (incomingBuffer.hasRemaining()) {
            char current = (char) incomingBuffer.get();
            if (current == '\r' || current == '\n') {
                
            }
            request.add();
        }
        
        return new String(request.st);
    }

}
