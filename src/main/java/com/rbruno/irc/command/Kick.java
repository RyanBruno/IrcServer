package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.events.ChannelChangedEvent;
import com.rbruno.irc.events.ChannelChangedEvent.ChannelChangeType;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Error;

public class Kick extends Command {

    public Kick(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public Response apply(Request request) {
        Config config = getModule().getConfig();
        Response response = new Response(config.getHostname());

        Client client = getModule().getClient(request.getChannel());
        if (client == null) {
            response.addUnknownRegCommand("KICK");
            return response;
        }

        Channel channel = getModule().getChannel(request.getArgs()[0]);

        if (channel == null) {
            response.addMessage(Error.ERR_NOSUCHCHANNEL, client, request.getArgs()[1] + " :No such channel");
            return response;
        }

        if (!channel.isChanOp(client) && !getModule().isop(client)) {
            response.addMessage(Error.ERR_CHANOPRIVSNEEDED, client, request.getArgs()[1] + " :You're not channel operator");
            return response;
        }

        // TODO check args length
        if (!getModule().isNickInUse(request.getArgs()[1])) {
            response.addMessage(Error.ERR_NOSUCHNICK, client, request.getArgs()[1] + " :No such nick");
            return response;
        }

        if (!channel.hasClient(client.getNickname())) {
            response.addMessage(Error.ERR_USERNOTINCHANNEL, client, request.getArgs()[1] + " " + channel.getName() + " :User is not on that channel");
            return response;
        }

        String message = null;
        if (request.getArgs().length >= 3) {
            message = request.getArgs()[2];
        }

        constructKickMessage(response, channel, client, request.getArgs()[1], message);

        channel = channel.removeClient(request.getArgs()[1]);
        getModule().getEventDispacher().dispach(new ChannelChangedEvent(channel, ChannelChangeType.CLIENT_PART_CHANNEL));
    }

    private void constructKickMessage(Response response, Channel channel, Client client, String target, String message) {
        if (message == null) {
            message = "You have been kicked from the channel";
        }

        response.addMessage(channel.getClients(), ":" + client.getAbsoluteName() + " KICK " + channel.getName() + " " + target + ":" + message);
    }

}
