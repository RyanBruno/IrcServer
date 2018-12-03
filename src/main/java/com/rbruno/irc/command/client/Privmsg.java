package com.rbruno.irc.command.client;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Privmsg extends Command {

    public Privmsg() {
        super("PRIVMSG", 2);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        Stream<Channel> channels = Arrays.stream(request.getArgs()[0].split(",")).filter(s -> s.startsWith("#") || s.startsWith("&")).map(new Function<String, Channel>() {
            @Override
            public Channel apply(String name) {
                Channel target = Server.getServer().getChannelManger().getChannel(name);
                if (target == null) {
                    request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), name + " :No such channel");
                }
                return target;
            }

        });

        channels = channels.filter(c -> c != null);

        channels.forEach(new Consumer<Channel>() {
            public void accept(Channel channel) {
                if (!channel.hasClient(client.get()) && channel.getModes().isNoOutsideMessages()) {
                    request.getConnection().send(Error.ERR_CANNOTSENDTOCHAN, client.get(), channel.getName() + " :Cannot send to channel");
                    return;
                }
                if (!channel.hasClient(client.get()) && channel.getModes().isModerated()) {
                    request.getConnection().send(Error.ERR_CANNOTSENDTOCHAN, client.get(), channel.getName() + " :Cannot send to channel");
                    return;
                }
                channel.sendToAll(":" + client.get().getAbsoluteName() + " PRIVMSG " + channel.getName() + " " + request.getArgs()[1]);
            };
        });

        Stream<Client> clients = Arrays.stream(request.getArgs()[0].split(",")).filter(s -> !s.startsWith("{#,&}")).map(new Function<String, Client>() {

            @Override
            public Client apply(String name) {
                Client target = Server.getServer().getClientManager().getClient(name);
                if (target == null) {
                    request.getConnection().send(Error.ERR_NOSUCHNICK, client.get(), name + " :No such nick");
                }
                return target;
            }

        });

        clients = clients.filter(c -> c != null);

        clients.forEach(c -> request.getConnection().send(":" + client.get().getAbsoluteName() + " PRIVMSG " + client.get().getNickname() + " " + request.getArgs()[1]));

    }

}
