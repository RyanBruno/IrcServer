package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;

public class Nick extends Command {

  public Nick() {
    super("NICK", 1);
  }

  @Override
  public void execute(Request request, Optional<Client> client) {
    if (client.isPresent()) {
      // Change nick
    } else {
      //TODO Check for illegal chars and length
      request.getConnection().setNickName(request.getArgs()[0]);
    }
  }

}
