package com.rbruno.irc.command.client;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class List extends Command {

    public List() {
        super("LIST", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        // TODO Fix
        super.execute(request, client);
        Iterator<Channel> channels;
        if (request.getArgs().length == 0) {
            channels = Server.getServer().getChannelManger().getChannels().iterator();
        } else {
            channels = Arrays.stream(request.getArgs()[0].split(",")).map((c) -> Server.getServer().getChannelManger().getChannel(c)).filter(c -> !c.getModes().isSecrete()).iterator();
        }
        request.getConnection().send(Reply.RPL_LISTSTART, client.get(), "Channel :Users Name");

        channels.forEachRemaining(c -> request.getConnection().send(Reply.RPL_LIST, client.get(), c.getName() + " " + c.getUsersCount() + " :" + c.getTopic()));
        request.getConnection().send(Reply.RPL_LISTEND, client.get(), ":End of /LIST");
    }

}
