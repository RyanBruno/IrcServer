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

    private CommandInvoker invoker;

    protected Socket socket;
    protected BufferedReader reader;

    public Connection(Socket socket, CommandInvoker invoker) {
        this.socket = socket;
        this.invoker = invoker;
    }

    /**
     * Start listing on socket. When a line is received it creates a new Request
     * object and send to Command.runCommnd(Request)
     */
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            while (!socket.isClosed()) {
                Request request = getNextRequest(reader);

                if (request == null) {
                    close(Optional.empty());
                    break;
                }
                invoker.runCommand(request, client);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request getNextRequest(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        if (line == null) {
            return null;
        } else {
            return new Request(this, line);
        }
    }

    public void setClient(Client client) {
        this.client = Optional.of(client);
        this.nickname = Optional.empty();
    }

    public void setNickname(String nickname) {
        this.nickname = Optional.of(nickname);
    }

    public void setInvoker(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public Optional<String> getNickname() {
        if (client.isPresent())
            return Optional.of(client.get().getNickname());
        return nickname;
    }

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
