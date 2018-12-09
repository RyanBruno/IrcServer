package com.rbruno.irc.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.rbruno.irc.events.Module;
import com.rbruno.irc.events.NewRequestEvent;

public class IrcConnection {

    private Module module;

    private SocketChannel socketChannel;
    private ByteBuffer incomingBuffer;

    public IrcConnection(SocketChannel socketChannel, Module module) {
        this.socketChannel = socketChannel;
        this.module = module;
        incomingBuffer = ByteBuffer.allocate(512);
    }

    public void read() throws IOException {
        socketChannel.read(incomingBuffer);

        incomingBuffer.flip();
        int index;
        while ((index = readForIndex()) != -1) {
            byte[] readBuffer = new byte[index - incomingBuffer.position()];
            incomingBuffer.get(readBuffer);

            Request request = new Request(socketChannel, new String(readBuffer));
            module.getEventDispacher().dispach(new NewRequestEvent(request));
        }
        incomingBuffer.compact();
        incomingBuffer.flip();
    }

    // TODO Move to utils
    private int readForIndex() {
        incomingBuffer.mark();

        boolean cReturn = false;
        while (incomingBuffer.hasRemaining()) {
            char current = (char) incomingBuffer.get();

            if (cReturn) {
                if (current == '\n') {
                    int postion = incomingBuffer.position();
                    incomingBuffer.reset();
                    return postion;
                } else {
                    cReturn = false;
                }
            } else if (current == '\r') {
                cReturn = true;
            }
        }
        incomingBuffer.reset();
        return -1;
    }

}
