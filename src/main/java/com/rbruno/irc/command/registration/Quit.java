package com.rbruno.irc.command.registration;

import java.util.Optional;

import com.rbruno.irc.command.CommandModule;
import com.rbruno.irc.command.RegistrationCommand;
import com.rbruno.irc.net.Request;

public class Quit extends RegistrationCommand {

    public Quit(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request) {
        String message = null;
        if (request.getArgs().length != 0) {
            message = request.getArgs()[0];
        }
        // TODO QUIT event
        request.getConnection().close(Optional.ofNullable(message));        
    }

}
