package com.rbruno.irc.command.client;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.IrcMessenger;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;

public class Names extends Command {

    public Names() {
        super("NAMES", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        IrcMessenger ircMessenger = new IrcMessenger();
        
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

        channels.forEach(c -> ircMessenger.sendNames(c, client.get(), c.getIterator()));

    }

}
