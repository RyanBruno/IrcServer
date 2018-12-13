package com.rbruno.irc.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Info extends Command {

    public Info(CommandModule commandModule) {
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

        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(Info.class.getResourceAsStream("/info.txt")));
            inputStream.lines().forEach(l -> response.addMessage(Reply.RPL_INFO, client, ":" + l));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        response.addMessage(Reply.RPL_ENDOFINFO, client, ":End of /INFO list");
        
        return response;
    }

}
