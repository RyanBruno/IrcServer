package com.rbruno.irc.command.commands;

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
        
        for (String name : request.getArgs()[0].split(","))  {
            
            if (!name.startsWith("&") && !name.startsWith("#")) {
                request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), name + " :No such channel");
                return;
            }
            
            Channel channel = Server.getServer().getChannelManger().getChannel(name);

            if (channel == null) {
                channel = new LocalChannel(name);
                Server.getServer().getChannelManger().addChannel(channel);
            }
            
            if (channel.hasClient(client.get()))
                return;

            // TODO Fix conditions
            if (channel.getModes().isInviteOnly()) {
                request.getConnection().send(Error.ERR_INVITEONLYCHAN, client.get(), channel.getName() + " :Cannot join channel (+i)");
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
                request.getConnection().send(Error.ERR_BADCHANNELKEY, client.get(), channel.getName() + " :Cannot join channel (+k)");
                return;
            }

            channel.addClient(client.get());

        }

    }
}
