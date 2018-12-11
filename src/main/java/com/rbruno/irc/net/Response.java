package com.rbruno.irc.net;

import java.util.HashMap;
import java.util.Map;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;;

public class Response {
    private Map<Client[], String> responseMap = new HashMap<>();
    private String hostname;
    
    public Response(String hostname) {
        this.hostname = hostname;
    }

    public boolean addMessage(Client[] client, String message) {
        return responseMap.put(client, message) == null;
    }

    public boolean addMessage(Client[] client, String prefix, String command, String args) {
        String message = ":" + prefix + " " + command + " " + args;
        return addMessage(client, message);
    }

    public boolean addMessage(int code, Client client, String args) {
        String stringCode = code + "";
        if (stringCode.length() < 2)
            stringCode = "0" + stringCode;
        if (stringCode.length() < 3)
            stringCode = "0" + stringCode;

        String message = ":" + hostname + " " + stringCode + " " + client.getNickname() + " " + args;

        Client[] clients = new Client[] {client};
        return addMessage(clients, message);
    }

    public boolean addMessage(Reply reply, Client client, String args) {
        return addMessage(reply.getCode(), client, args);
    }

    public boolean addMessage(Error error, Client client, String args) {
        return addMessage(error.getCode(), client, args);
    }

    
    public Map<Client[], String> getResponseMap() {
        return responseMap;
    }
}
