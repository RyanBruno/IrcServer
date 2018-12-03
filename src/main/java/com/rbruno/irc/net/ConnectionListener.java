package com.rbruno.irc.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.rbruno.irc.Server;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewConnectionEvent;
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
    	new Connection(eventDispacher, event.getSocket());
    }


}
