package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Away extends Command {

  public Away() {
    super("AWAY", 0);
  }

  @Override
  public void execute(Request request, Optional<Client> client) {
    super.execute(request, client);
    String message = "";
    if (request.getArgs().length != 0)
      message = request.getArgs()[0];
    if (message.startsWith(":"))
      message = message.substring(1);
    client.get().setAwayMessage(message);
    if (client.get().getAwayMessage().equals("")) {
      request.getConnection().send(Reply.RPL_UNAWAY, client.get(), ":You are no longer marked as being away");
    } else {
      request.getConnection().send(Reply.RPL_NOWAWAY, client.get(), ":You have been marked as being away");
    }
  }
}
