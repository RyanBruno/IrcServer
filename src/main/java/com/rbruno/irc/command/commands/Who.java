package com.rbruno.irc.command.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Who extends Command {

    public Who() {
        super("WHO", 1);
    }

    @Override
    public void execute(Request request, Client client) {
        String target = request.getArgs()[0];
        Channel channel = Server.getServer().getChannelManger().getChannel(target);
        if (channel == null) {
            request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), " :No such channel");
            return;
        }
        for (LocalClient current : channel.getClients())
            request.getConnection().send(Reply.RPL_WHOREPLY, client.get(), channel.getName() + " " + current.getUsername() + " * " + current.getHostname() + " " + current.getNickname() + " H+ :" + current.getHopCount() + " " + current.getRealName());
        request.getConnection().send(Reply.RPL_ENDOFWHO, client.get(), target + " :End of /WHO list");

    }

}
