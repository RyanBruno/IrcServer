package com.rbruno.irc.command.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Info extends Command {

    public Info() {
        super("INFO", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(Info.class.getResourceAsStream("/info.txt")));
            inputStream.lines().forEach(l -> request.getConnection().send(Reply.RPL_INFO, client.get(), ":" + l));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.getConnection().send(Reply.RPL_ENDOFINFO, client.get(), ":End of /INFO list");
    }

}
