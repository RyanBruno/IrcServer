package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.CommandInvoker;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Connection implements Runnable {

    private Optional<Client> client = Optional.empty();
    private Optional<String> nickname = Optional.empty();
    protected Socket socket;

    public boolean send(byte[] block) {

        try {
            socket.getOutputStream().write(block);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean send(String message) {
        if (socket.isClosed())
            return false;
        System.out.println(message);
        return send(message.concat("\r\n").getBytes());
    }

    public boolean send(String prefix, String command, String args) {
        String message = ":" + prefix + " " + command + " " + args;
        return send(message);
    }

    public boolean send(int code, String nickname, String args) {
        String stringCode = code + "";
        if (stringCode.length() < 2)
            stringCode = "0" + stringCode;
        if (stringCode.length() < 3)
            stringCode = "0" + stringCode;

        String message = ":" + Server.getServer().getConfig().getHostname() + " " + stringCode + " " + nickname + " " + args;
        return send(message);
    }

    public boolean send(Reply reply, String nickname, String args) {
        return send(reply.getCode(), nickname, args);
    }

    public boolean send(Reply reply, Client client, String args) {
        return send(reply.getCode(), client.getNickname(), args);
    }

    public boolean send(Error error, String nickname, String args) {
        return send(error.getCode(), nickname, args);
    }

    public boolean send(Error error, Client client, String args) {
        return send(error.getCode(), client.getNickname(), args);
    }

    public void close(Optional<String> message) {
        if (client.isPresent()) {
            Server.getServer().getChannelManger().clientDisconnected(client.get());
            if (message.isPresent())
                Server.getServer().getClientManager().removeClient(client.get(), message);
        }

        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
