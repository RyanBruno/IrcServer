package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public abstract class Command {

  protected int args;

  public Command(String command, int args) {
    this.args = args;
  }

  public void execute(Request request, Optional<Client> client) {
    if (client.isPresent()) {
      client.get().setLastCheckin(System.currentTimeMillis());
    }

    if (request.getArgs().length < args) {
      if (client.isPresent()) {
        request.getConnection().send(Error.ERR_NEEDMOREPARAMS, client.get(), ":Not enough parameters");
      }
      return;
    }
  }

}
