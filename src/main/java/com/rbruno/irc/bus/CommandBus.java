package com.rbruno.irc.bus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public class CommandBus implements Bus<Request, Response, String> {
    
    Map<String, Function<Request, Response>> map = new HashMap<>();

    @Override
    public void forwardTo(String key, Function<Request, Response> responder) {
        map.put(key.toUpperCase(), responder);
    }

    @Override
    public Response apply(String key, Request request) {
        return map.get(key.toUpperCase()).apply(request);
    }
    
}
