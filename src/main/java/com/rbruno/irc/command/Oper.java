package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Oper extends Command {

    public Oper() {
        super("OPER", 2);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        if (Server.getServer().getOperManager().checkOpPassword(request.getArgs()[0], request.getArgs()[1], client.get())) {
            Server.getServer().getOperManager().addop(client.get());
            request.getConnection().send(Reply.RPL_YOUREOPER, client.get().getNickname(), ":You are now an IRC operator");
        } else {
            request.getConnection().send(Error.ERR_PASSWDMISMATCH, client.get(), ":Password incorrect");
        }
    }
}
