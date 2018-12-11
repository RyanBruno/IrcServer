package com.rbruno.irc.net;

import io.netty.channel.Channel;

/**
 * Phrases and stores information on a request made by a client.
 */
public class Request {

    private String prefix;
    private String command;
    private String[] args;
    private Channel channel;

    /**
     * Creates a new Request object. Phrases the line into prefix, command and
     * arguments.
     * 
     * @param connection Connection the request came from.
     * @param line       The line that was sent.
     * @throws Exception
     */
    public Request(Channel channel, String line) {
        // TODO Yell if malformed
        this.channel = channel;
        if (line.startsWith(":")) {
            this.prefix = line.split(" ")[0].substring(1);
            line = line.substring(prefix.length() + 1);
        }
        this.command = line.split(" ")[0];
        if (line.length() == command.length()) {
            line = line.substring(command.length());
        } else {
            line = line.substring(command.length() + 1);
        }
        this.args = line.trim().split(":")[0].split(" ");
        if (args.length == 1 && args[0].equals(""))
            args = new String[0];

        if (line.split(":").length > 1) {
            String[] newArray = new String[args.length + 1];
            for (int i = 0; i < args.length; i++)
                newArray[i] = args[i];

            newArray[newArray.length - 1] = line.split(":")[1];
            args = newArray;
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public Channel getChannel() {
        return channel;
    }
}