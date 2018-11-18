package com.rbruno.irc.command.commands;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Names extends Command {

    public Names() {
        super("NAMES", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        Stream<Channel> channels;

        if (request.getArgs().length == 0) {
            channels = Server.getServer().getChannelManger().getChannels().stream().filter(c -> c.hasClient(client.get()));
        } else {
            channels = Stream.of(request.getArgs()[0].split(",")).map(new Function<String, Channel>() {
                @Override
                public Channel apply(String name) {
                    return Server.getServer().getChannelManger().getChannel(name);
                }
            }).filter(c -> c != null);
        }

        channels.forEach(new Consumer<Channel>() {

            @Override
            public void accept(Channel channel) {
                String message = channel.getName() + " :";
                Iterator<Client> clients = channel.getIterator();

                while (clients.hasNext()) {
                    Client client = clients.next();
                    if (channel.isChanOp(client) || Server.getServer().getOperManager().isop(client)) {
                        message = message + "@" + client.getNickname() + " ";
                    } else {
                        message = message + "+" + client.getNickname() + " ";
                    }
                }

                request.getConnection().send(Reply.RPL_NAMREPLY, client.get(), message);
            }
        });

    }

}
