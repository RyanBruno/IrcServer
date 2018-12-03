package com.rbruno.irc.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewConnectionEvent;
import com.rbruno.irc.events.ServerOpenEvent;

public class ConnectionListener extends EventListener {

    private EventDispacher eventDispacher;

    @Override
    public void onServerOpen(ServerOpenEvent event) {
        try {
            Selector selector = Selector.open();
            event.getServerSocketChannel().register(selector, SelectionKey.OP_ACCEPT);
            
            while (true) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();

                    
                    if (!key.isValid()) {
                        // TODO closed
                        return;
                    }
                    
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        readBuffer.clear();
                        
                        int read = channel.read(readBuffer);
                        
                        if (read == -1) {
                            // TODO
                            continue;
                        }
                        
                        

                        readBuffer.flip();
                    }
                    
                    keys.remove();

                    
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = event.getServerSocketChannel().accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        // EMIT new connection
                    }

                    
                    if (key.isWritable()) {
                        
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewConnection(NewConnectionEvent event) {
        new Connection(eventDispacher, event.getSocket());
    }

}
