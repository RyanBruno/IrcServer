package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.LocalClient;

public class ClientConnection extends BaseConnection implements Connection, Runnable {

  private Connection base;
  private LocalClient client;
  protected BufferedReader reader;

  public ClientConnection(Connection base, LocalClient client) {
    super(null);
    this.base = base;
    this.client = client;
  }

  @Override
  public void run() {
    try {
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
      while (true) {
        Request request = getNextRequest(reader);

        if (request == null) {
          close();
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
  public void close() {
    try {
      reader.close();
      base.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean send(byte[] block) {
    return base.send(block);
  }

  @Override
  public void setNickname(String nickname) {
      client.setNickname(nickname);
  }

  @Override
  public Optional<String> getNickname() {
    return Optional.of(client.getNickname());
  }
}
