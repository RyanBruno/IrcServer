package com.rbruno.irc.oper;

import java.util.Iterator;

import com.rbruno.irc.client.Client;

public interface OperManager {

    public Iterator<Client> getOpLine();

    public boolean isop(Client client);

    public boolean checkOpPassword(String user, String password, Client client);

    public void addop(Client client);

    public void deop(Client client);

}
