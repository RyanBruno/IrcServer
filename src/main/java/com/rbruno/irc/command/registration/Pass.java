package com.rbruno.irc.command.registration;

import com.rbruno.irc.command.CommandModule;
import com.rbruno.irc.command.RegistrationCommand;
import com.rbruno.irc.net.Request;

public class Pass extends RegistrationCommand {

    public Pass(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request) {
        // TODO
    }
}
