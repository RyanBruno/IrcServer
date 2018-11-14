package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;

public class ClientConnection extends BaseConnection implements Connection, Runnable {

  private IrcConnection base;
  private Client client;

  public ClientConnection(Socket socket, IrcConnection base, Client client) {
    super(socket);
    this.base = base;
    this.client = client;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Request request = getNextRequest(base.reader);

        if (request == null) {
          // TODO Quit
        }
        Server.getServer().getCommandInvoker().runCommand(request, Optional.ofNullable(client));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Request getNextRequest(BufferedReader reader) throws IOException {
    return base.getNextRequest(reader);
  }

  @Override
  public void setNickName(String nickname) {
  }
}
