package com.rbruno.irc.command.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.ClientCommand;
import com.rbruno.irc.command.CommandModule;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Info extends ClientCommand {

    public Info(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request, Client client) {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(Info.class.getResourceAsStream("/info.txt")));
            inputStream.lines().forEach(l -> request.getConnection().send(Reply.RPL_INFO, client, ":" + l));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.getConnection().send(Reply.RPL_ENDOFINFO, client.get(), ":End of /INFO list");
    }

}
