package com.rbruno.irc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.net.Connection;

public class Networking {

    private ServerSocket serverSocket;

    public Networking() throws IOException {
        serverSocket = new ServerSocket(Server.getServer().getConfig().getPort());

        Logger.log("Started Server on port: " + serverSocket.getLocalPort());

        run();
    }

    /**
     * Main running thread. Waits for sockets then creates a new Connection object
     * on a new thread.
     */
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread connection = new Thread(new Connection(socket, Server.getServer().getRegCommandInvoker()));
                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
