package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Optional;

import com.rbruno.irc.Server;

public class RegistrationConnection extends BaseConnection implements Connection, Runnable {

  private Optional<String> nickname;

  protected BufferedReader reader;

  public RegistrationConnection(Socket socket) {
    super(socket);
  }

  /**
   * Start listing on socket. When a line is received it creates a new Request
   * object and send to Command.runCommnd(Request)
   */
  @Override
  public void run() {
    try {
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
      while (!closed) {
        Request request = getNextRequest(reader);

        if (request == null) {
          close();
        }
        Server.getServer().getCommandInvoker().runCommand(request, Optional.empty());
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Request getNextRequest(BufferedReader reader) throws IOException {
    String line = reader.readLine();

    if (line == null) {
      return null;
    } else {
      return new Request(this, line);
    }
  }

  @Override
  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.close();
  }

  @Override
  public void setNickname(String nickname) {
    this.nickname = Optional.of(nickname);
  }

  @Override
  public Optional<String> getNickname() {
    return nickname;
  }

}
