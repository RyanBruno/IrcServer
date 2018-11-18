package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Kick extends Command {

    public Kick() {
        super("KICK", 2);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        Channel channel = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);
        if (channel == null) {
            request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), request.getArgs()[1] + " :No such channel");
            return;
        }
        if (!channel.isChanOp(client.get()) && !Server.getServer().getOperManager().isop(client.get())) {
            request.getConnection().send(Error.ERR_CHANOPRIVSNEEDED, client.get(), request.getArgs()[1] + " :You're not channel operator");
            return;
        }
        Client target = Server.getServer().getClientManager().getClient(request.getArgs()[1]);
        if (target == null) {
            request.getConnection().send(Error.ERR_NOSUCHNICK, client.get(), request.getArgs()[1] + " :No such nick");
            return;
        }
        if (!channel.hasClient(client.get())) {
            request.getConnection().send(Error.ERR_USERNOTINCHANNEL, client.get(), target.getNickname() + " " + channel.getName() + " :User is not on that channel");
            return;
        }

        String message = ":You have been kicked from the channel";
        if (request.getArgs().length >= 3)
            message = request.getArgs()[2];
        channel.sendToAll(":" + client.get().getAbsoluteName() + " KICK " + channel.getName() + " " + target.getNickname() + " " + message);
        channel.removeClient(target);
    }

}
