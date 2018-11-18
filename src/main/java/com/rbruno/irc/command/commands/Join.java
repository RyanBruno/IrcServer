package com.rbruno.irc.command.commands;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.LocalChannel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Join extends Command {

    public Join() {
        super("JOIN", 1);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        Stream<Channel> channels = Arrays.stream(request.getArgs()[0].split(",")).map(new Function<String, Channel>() {
            @Override
            public Channel apply(String name) {
                if (!name.startsWith("&") && !name.startsWith("#")) {
                    request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), name + " :No such channel");
                }

                Channel channel = Server.getServer().getChannelManger().getChannel(name);

                if (channel == null) {
                    channel = new LocalChannel(name);
                }

                return channel;
            }
        });

        channels.filter(c -> c != null);

        channels.forEach(new Consumer<Channel>() {
            @Override
            public void accept(Channel channel) {

                if (channel.hasClient(client.get()))
                    return;

                if (channel.isInviteOnly()) {
                    // TODO
                }

                if (channel.isBanned(client.get())) {
                    // TODO
                }

                // TODO Limit

                if (channel.getPassword().isPresent()) {
                    // TODO
                }

                channel.addClient(client.get());
                request.getConnection().send(Reply.RPL_TOPIC, client.get(), channel.getName() + " :" + channel.getTopic());

            }
        });

    }
}
