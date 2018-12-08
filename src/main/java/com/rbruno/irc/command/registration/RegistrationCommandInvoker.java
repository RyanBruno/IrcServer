package com.rbruno.irc.command.registration;

public class RegistrationCommandInvoker {

    public static void registerEventListeners(RegCommandModule commandModule) {
        commandModule.getEventDispacher().registerListener(new Pass(commandModule));
        commandModule.getEventDispacher().registerListener(new Nick(commandModule));
        commandModule.getEventDispacher().registerListener(new User(commandModule));

    }

}
