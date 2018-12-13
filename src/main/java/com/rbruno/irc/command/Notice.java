package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Notice extends Command {

    public Notice() {
        super("NOTICE", 2);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        Client target = Server.getServer().getClientManager().getClient(request.getArgs()[0]);
        if (target == null) {
            request.getConnection().send(Error.ERR_NOSUCHNICK, target, request.getArgs()[0] + " :No such nick");
            return;
        }
        request.getConnection().send(":" + client.get().getAbsoluteName() + " NOTICE " + target.getNickname() + " " + request.getArgs()[1]);

    }
}
