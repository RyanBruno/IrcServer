package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.events.ClientChangedEvent;
import com.rbruno.irc.events.ClientChangedEvent.ClientChangeType;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Away extends Command {

    public Away(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public Response apply(Request request) {
        Config config = getModule().getConfig();
        Response response = new Response(config.getHostname());
        
        Client client = getModule().getClient(request.getChannel());
        if (client == null) {
            response.addUnknownRegCommand("AWAY");
            return response;
        }
        
        if (request.getArgs().length == 0 || request.getArgs()[0].equals("")) {
            if (client.getAwayMessage() != null || !client.getAwayMessage().equals("")) {
                client = client.setAwayMessage(null);
            }
            response.addMessage(Reply.RPL_UNAWAY, client, ":You are no longer marked as being away");
            getModule().getEventDispacher().dispach(new ClientChangedEvent(client, ClientChangeType.UNAWAY));
        } else {
            client = client.setAwayMessage(request.getArgs()[0]);
            response.addMessage(Reply.RPL_NOWAWAY, client, ":You have been marked as being away");
            getModule().getEventDispacher().dispach(new ClientChangedEvent(client, ClientChangeType.AWAY_SET));
        }

        return response;
    }
}
