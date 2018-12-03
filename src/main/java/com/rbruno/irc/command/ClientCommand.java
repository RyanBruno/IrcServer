package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.Request;

public interface ClientCommand {

    public void execute(Request request, Client client);

}
