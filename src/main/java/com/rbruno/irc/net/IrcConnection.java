package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Optional;

import com.rbruno.irc.Server;

public class IrcConnection extends BaseConnection implements Connection, Runnable {
  
  public String nickname;
  
  protected BufferedReader reader;

  public IrcConnection(Socket socket) {
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
      while (true) {
        Request request = getNextRequest(reader);

        if (request == null) {
          // TODO Quit
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
  public void setNickName(String nickname) {
    this.nickname = nickname;
  }

}
