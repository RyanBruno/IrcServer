package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.Request;

public interface CommandInvoker {
  
  public void runCommand(Request request, Optional<Client> command);

}
