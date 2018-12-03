package com.rbruno.irc.command.registration;

import com.rbruno.irc.command.RegistrationCommand;
import com.rbruno.irc.net.Request;

public class Nick implements RegistrationCommand {

    public Nick() {
    }

    @Override
    public void execute(Request request) {
        // TODO FIx
        request.getConnection().setNickname(request.getArgs()[0]);
    }

}
