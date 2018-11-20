package com.rbruno.irc.command.commands;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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
        super.execute(request, client);
        Iterator<Channel> channels;
        if (request.getArgs().length == 0) {
            channels = Server.getServer().getChannelManger().getChannels().iterator();
        } else {
            channels = Arrays.stream(request.getArgs()[0].split(",")).map(new Function<String, Channel>() {

                @Override
                public Channel apply(String channel) {
                    return Server.getServer().getChannelManger().getChannel(channel);
                }
            }).filter(c -> !c.getModes().isSecrete()).iterator();
        }
        request.getConnection().send(Reply.RPL_LISTSTART, client.get(), "Channel :Users Name");

        channels.forEachRemaining(new Consumer<Channel>() {

            @Override
            public void accept(Channel current) {
                request.getConnection().send(Reply.RPL_LIST, client.get(), current.getName() + " " + current.getUsersCount() + " :" + current.getTopic());
            }

        });
        request.getConnection().send(Reply.RPL_LISTEND, client.get(), ":End of /LIST");
    }

}
