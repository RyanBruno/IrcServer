package com.rbruno.irc.command;

import com.rbruno.irc.command.registration.Nick;
import com.rbruno.irc.command.registration.Pass;
import com.rbruno.irc.command.registration.User;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewRequestEvent;

public class RegistrationCommandInvoker extends EventListener {

    private RegistrationCommand pass;
    private RegistrationCommand nick;
    private RegistrationCommand user;

    public RegistrationCommandInvoker(CommandModule commandModule) {
        pass = new Pass(commandModule);
        nick = new Nick(commandModule);
        user = new User(commandModule);
    }
    
    @Override
    public void onNewRequest(NewRequestEvent event) {
        switch (event.getRequest().getCommand().toLowerCase()) {
        case "pass":
            pass.execute(event.getRequest());
            break;
        case "nick":
            nick.execute(event.getRequest());
            break;
        case "user":
            user.execute(event.getRequest());
            break;
        }
    }

}
