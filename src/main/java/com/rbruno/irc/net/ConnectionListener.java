package com.rbruno.irc.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewRequestEvent;
import com.rbruno.irc.events.SendDataEvent;
import com.rbruno.irc.events.ServerOpenEvent;

public class ConnectionListener extends EventListener {

    private EventDispacher eventDispacher;

    private Map<SocketChannel, byte[]> dataTracking = new HashMap<>();

    @Override
    public void onServerOpen(ServerOpenEvent event) {
        try {
            Selector selector = Selector.open();
            event.getServerSocketChannel().register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();

                    keys.remove();

                    if (!key.isValid()) {
                        // TODO closed
                        return;
                    }

                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = event.getServerSocketChannel().accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        // EMIT new connection
                    }

                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        readBuffer.clear();

                        int read = socketChannel.read(readBuffer);

                        if (read == -1) {
                            // TODO
                            continue;
                        }
                        Request request = new Request(socketChannel, readBuffer.asCharBuffer().toString());
                        eventDispacher.dispach(new NewRequestEvent(request));
                    }

                    if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();

                        byte[] data = dataTracking.get(channel);
                        if (data != null) {
                            dataTracking.remove(channel);

                            channel.write(ByteBuffer.wrap(data));
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSendData(SendDataEvent event) {
        dataTracking.put(event.getSocketChannel(), event.getData());
    }
}
