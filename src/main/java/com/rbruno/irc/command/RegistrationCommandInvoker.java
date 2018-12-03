package com.rbruno.irc.command;

import com.rbruno.irc.command.registration.Nick;
import com.rbruno.irc.command.registration.Pass;
import com.rbruno.irc.command.registration.User;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewCommandEvent;

public class RegistrationCommandInvoker extends EventListener {

    private Pass pass;
    private Nick nick;
    private User user;

    public RegistrationCommandInvoker() {
        pass = new Pass();
        nick = new Nick();
        user = new User();
    }
    
    @Override
    public void onNewCommand(NewCommandEvent event) {
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
        default:
            break;
        }
    }

}
