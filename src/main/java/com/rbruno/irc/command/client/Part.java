package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Part extends Command {

    public Part() {
        super("PART", 1);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);

        for (String name : request.getArgs()[0].split(",")) {
            Channel channel = Server.getServer().getChannelManger().getChannel(name);

            if (channel == null) {
                request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), name + " :No such channel");
                continue;
            }
            
            String message = null;
            if (request.getArgs().length != 0) {
                message = request.getArgs()[0];
            }

            channel.partClient(client.get(), Optional.ofNullable(message));
        }
    }

}
