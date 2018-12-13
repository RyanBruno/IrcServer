package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Admin extends Command {

    public Admin(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public Response apply(Request request) {
        Config config = getModule().getConfig();
        Response response = new Response(config.getHostname());
        
        Client client = getModule().getClient(request.getChannel());
        if (client == null) {
            response.addUnknownRegCommand("ADMIN");
            return response;
        }

        response.addMessage(Reply.RPL_ADMINME, client, config.getHostname() + " :Administrative info");

        if (config.getAdminLoc1().isPresent()) {
            response.addMessage(Reply.RPL_ADMINLOC1, client, config.getHostname() + " :" + config.getAdminLoc1().get());
            if (config.getAdminLoc2().isPresent()) {
                response.addMessage(Reply.RPL_ADMINLOC2, client, config.getHostname() + " :" + config.getAdminLoc2().get());
                if (config.getAdminMail().isPresent()) {
                    response.addMessage(Reply.RPL_ADMINMAIL, client, config.getHostname() + " :" + config.getAdminMail().get());
                }
            }
        }
        return response;
    }
}
