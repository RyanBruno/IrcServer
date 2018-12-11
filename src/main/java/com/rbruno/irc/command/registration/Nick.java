package com.rbruno.irc.command.registration;

import java.util.function.Function;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.NickSetEvent;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Error;

public class Nick extends RegistrationCommand implements Function<Request, Response> {

    public Nick(RegCommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public Response apply(Request request) {
        Response response = new Response(getCommandModule().getHostname());
        if (request.getArgs().length > 0) {
            if (getCommandModule().isNickInUse(request.getArgs()[0])) {
                response.addMessage(new Client[0], getCommandModule().getHostname(), Error.ERR_NICKNAMEINUSE + " ???", request.getArgs()[0] + " :Nickname is already in use");
            } else {
                getCommandModule().getEventDispacher().dispach(new NickSetEvent(request.getChannel(), request.getArgs()[0]));
            }
        } else {
            response.addMessage(new Client[0], getCommandModule().getHostname(), Error.ERR_NONICKNAMEGIVEN + " ???", ":No nickname given");
        }
        return response;
    }

}
