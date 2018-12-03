package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewCommandEvent;
import com.rbruno.irc.events.NewConnectionEvent;
import com.rbruno.irc.events.NewLineEvent;
import com.rbruno.irc.events.NewRequestEvent;
import com.rbruno.irc.events.ServerOpenEvent;

public class ConnectionListener extends EventListener {

    private EventDispacher eventDispacher;
    
    @SuppressWarnings("resource")
    @Override
    public void onServerOpen(ServerOpenEvent event) {
        ServerSocket serverSocket;
        
        try {
            serverSocket = new ServerSocket(Server.getServer().getConfig().getPort());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                eventDispacher.dispach(new NewConnectionEvent(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNewConnection(NewConnectionEvent event) {
        Client client = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(event.getSocket().getInputStream(), "UTF-8"));

            while (!event.getSocket().isClosed()) {
                String line = reader.readLine();

                if (line == null) {
                    break;
                }

                eventDispacher.dispach(new NewLineEvent(line, Optional.ofNullable(client)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO socket closed event
    }

    @Override
    public void onNewLine(NewLineEvent event) {
        eventDispacher.dispach(new NewRequestEvent(new Request(null, event.getLine()), event.getClient()));
    }
    
    @Override
    public void onNewRequest(NewRequestEvent event) {
        eventDispacher.dispach(new NewCommandEvent(event.getRequest(), event.getClient()));
    }

}
