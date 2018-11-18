package com.rbruno.irc.oper;

import java.util.ArrayList;
import java.util.Iterator;

import com.rbruno.irc.client.Client;

public class BasicOperManager implements OperManager {

    public ArrayList<Client> ops;
    
    public BasicOperManager() {
        ops = new ArrayList<Client>();
    }
    
    @Override
    public Iterator<Client> getOpLine() {
        return ops.iterator();
    }

    @Override
    public boolean isop(Client client) {
        return ops.contains(client);
    }

    @Override
    public boolean checkOpPassword(String user, String password, Client client) {
        return true;
    }

    @Override
    public void addop(Client client) {
        ops.add(client);
    }

    @Override
    public void deop(Client client) {
        ops.remove(client);
    }

}
