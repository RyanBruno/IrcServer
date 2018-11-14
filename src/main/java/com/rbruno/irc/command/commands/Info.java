package com.rbruno.irc.command.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.logging.Level;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Info extends Command {

  public Info() {
    super("INFO", 0);
  }

  @Override
  public void execute(Request request, Optional<Client> client) {
    if (request.getArgs().length == 0) {
      try {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(Info.class.getResourceAsStream("/info.txt")));
        while (inputStream.ready())
          request.getConnection().send(Reply.RPL_INFO, request.getClient(), ":" + inputStream.readLine());
      } catch (NullPointerException e) {
        Logger.log("Could not find info.txt in the jar.", Level.FINE);
      }
      request.getConnection().send(Reply.RPL_ENDOFINFO, request.getClient(), ":End of /INFO list");
    } else {
      // TODO: Server
    }
  }

}
