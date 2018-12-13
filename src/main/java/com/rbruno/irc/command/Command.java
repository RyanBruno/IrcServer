package com.rbruno.irc.command;

import java.util.function.Function;

import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public abstract class Command implements Function<Request, Response> {
    private CommandModule commandModule;

    public Command(CommandModule commandModule) {
        this.commandModule = commandModule;
    }

    @Override
    public abstract Response apply(Request t);

    public CommandModule getModule() {
        return commandModule;
    }
}
