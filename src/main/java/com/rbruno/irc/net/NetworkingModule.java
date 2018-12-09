package com.rbruno.irc.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.Module;

public class NetworkingModule extends Module {

    private ServerSocketChannel serverSocketChannel;
    private HashMap<SocketChannel, IrcConnection> connectionMap = new HashMap<>();

    public NetworkingModule(EventDispacher eventDispacher, ServerSocketChannel serverSocketChannel) {
        super(eventDispacher);
        this.serverSocketChannel = serverSocketChannel;
        try {
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                if (selector.select() <= 0) continue;

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();

                    keys.remove();

                    if (!key.isValid()) {
                        // TODO closed
                        throw new Exception("Invalid Key");
                    }

                    if (key.isAcceptable()) {
                        accept(key);
                    }

                    if (key.isReadable()) {
                        read(key);
                    }

                    if (key.isWritable()) {
                        write(key);
                    }
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(SelectionKey key) throws IOException {
        // TODO Writing
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        
        connectionMap.get(socketChannel).read();
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);

        connectionMap.put(socketChannel, new IrcConnection(socketChannel, this));
    }

    @Override
    public void registerEventListeners() {
        // TODO Writing
    }

}
