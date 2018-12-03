package com.rbruno.irc.command.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Time extends Command {

    public Time() {
        super("TIME", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        Date date = new Date();
        request.getConnection().send(Reply.RPL_TIME, client.get(), Server.getServer().getConfig().getHostname() + " :" + new Timestamp(date.getTime()));
    }
}
