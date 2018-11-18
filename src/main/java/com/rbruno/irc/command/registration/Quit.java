package com.rbruno.irc.command.registration;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;

public class Quit extends Command {

    public Quit() {
        super("QUIT", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        request.getConnection().close();
        if (!client.isPresent())
            return;
        
        String message = "Leaving";
        if (request.getArgs().length != 0)
            message = request.getArgs()[0];

        /*for (Channel current : request.getClient().getChannels()) {
            current.removeClient(request.getClient());
            for (Client client : current.getClients())
                client.getConnection().send(":" + request.getClient().getAbsoluteName() + " QUIT " + message);
        }*/
        // TODO: Notify all clients and servers

    }

}
