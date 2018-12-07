package com.rbruno.irc.command.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.ClientCommand;
import com.rbruno.irc.command.CommandModule;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Time extends ClientCommand {

    public Time(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request, Client client) {
        Date date = new Date();
        request.getConnection().send(Reply.RPL_TIME, client, Server.getServer().getConfig().getHostname() + " :" + new Timestamp(date.getTime()));
    }
}
