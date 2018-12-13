package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.LocalChannel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.events.ChannelChangedEvent;
import com.rbruno.irc.events.ChannelChangedEvent.ChannelChangeType;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Join extends Command {

    public Join(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public Response apply(Request request) {
        Config config = getModule().getConfig();
        Response response = new Response(config.getHostname());

        Client client = getModule().getClient(request.getChannel());
        if (client == null) {
            response.addUnknownRegCommand("INFO");
            return response;
        }

        String password;
        if (request.getArgs().length > 1) {
            password = request.getArgs()[1];
        }

        for (String name : request.getArgs()[0].split(",")) {

            if (!name.startsWith("&") && !name.startsWith("#")) {
                response.addMessage(Error.ERR_NOSUCHCHANNEL, client, name + " :No such channel");
                return response;
            }

            Channel channel = getModule().getChannel(name);

            if (channel == null) {
                channel = new LocalChannel(name, "Default topic.", new String[0]);
            }

            // TODO Check if client can join. ex. password, invite only...
            
            channel = channel.addClient(client.getNickname());
            constructJoinMessaged(response, channel, client);
            getModule().getEventDispacher().dispach(new ChannelChangedEvent(channel, ChannelChangeType.CLIENT_JOIN_CHANNEL));
        }

        return response;
    }

    private void constructJoinMessaged(Response response, Channel channel, Client client) {
        // Join message
        response.addMessage(channel.getClients(), ":" + client.getAbsoluteName() + " JOIN " + channel.getName());

        // Topic
        response.addMessage(Reply.RPL_TOPIC, client, channel.getName() + " :" + channel.getTopic());

        // Names
        String message = "@ " + channel.getName() + " :";
        for (Client current : channel.getClients()) {
            if (channel.isChanOp(client)) {
                message = message + "@" + current.getNickname() + " ";
            } else if (channel.hasVoice(current)) {
                message = message + "+" + current.getNickname() + " ";
            } else {
                message = message + current.getNickname() + " ";
            }
        }
        response.addMessage(Reply.RPL_NAMREPLY, client, message);
        response.addMessage(Reply.RPL_ENDOFNAMES, client, channel.getName() + " :End of /NAMES list.");
    }
}
