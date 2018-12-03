package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.LocalChannel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Join extends Command {

    public Join() {
        super("JOIN", 1);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        
        //TODO Fix Passwords
        String password = null;
        if (request.getArgs().length > 1) {
            password = request.getArgs()[1];
        }

        for (String name : request.getArgs()[0].split(",")) {

            if (!name.startsWith("&") && !name.startsWith("#")) {
                request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), name + " :No such channel");
                return;
            }

            Channel channel = Server.getServer().getChannelManger().getChannel(name);

            if (channel == null) {
                channel = new LocalChannel(name, password);
                Server.getServer().getChannelManger().addChannel(channel);
            }

            if (channel.hasClient(client.get()))
                return;

            if (channel.getModes().isInviteOnly()) {
                request.getConnection().send(Error.ERR_INVITEONLYCHAN, client.get(), channel.getName() + " :Cannot join channel (+i)");
                // TODO Fix invites
                return;
            }

            if (channel.isBanned(client.get())) {
                request.getConnection().send(Error.ERR_BANNEDFROMCHAN, client.get(), channel.getName() + " :Cannot join channel (+b)");
                return;
            }

            if (channel.getUsersCount() >= channel.getModes().getuserLimit()) {
                request.getConnection().send(Error.ERR_CHANNELISFULL, client.get(), channel.getName() + " :Cannot join channel (+l)");
                return;
            }

            if (channel.getModes().getPassword().isPresent()) {
                if (!channel.getModes().getPassword().get().equals(password)) {
                    request.getConnection().send(Error.ERR_BADCHANNELKEY, client.get(), channel.getName() + " :Cannot join channel (+k)");
                    return;
                }
            }

            channel.addClient(client.get());

        }

    }
}
