package com.rbruno.irc.net;

import java.io.IOException;
import java.net.Socket;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public abstract class BaseConnection implements Connection {

  protected Socket socket;
  protected boolean closed = true;

  public BaseConnection(Socket socket) {
    this.socket = socket;
  }

  @Override
  public boolean send(byte[] block) {

    try {
      socket.getOutputStream().write(block);
      socket.getOutputStream().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public void close() {
    closed = false;
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends message to the socket
   * 
   * @param message Message to be sent to connection.
   * @throws IOException
   */
  @Override
  public boolean send(String message) {
    if (socket.isClosed())
      return false;
    // if (server.getConfig().getProperty("debug").equals("true"))
    // System.out.println("[DeBug]" + message);
    return send(message.concat("\r\n").getBytes());
  }

  /**
   * Creates a formated string from the given arguments and passes them to
   * send(String). EX: :(prefix) (command) (args)
   * 
   * @param prefix  The prefix to be used in the message.
   * @param command The command to be used in the message.
   * @param args    The args to be used in the message.
   * @throws IOException
   */
  @Override
  public boolean send(String prefix, String command, String args) {
    String message = ":" + prefix + " " + command + " " + args;
    return send(message);
  }

  /**
   * Creates a formated string from the given arguments and passes them to
   * send(String).
   * 
   * @param code     The code to be used in the message.
   * @param nickname The nickname to be used in the message.
   * @param args     The arguments to be used in the message.
   * @throws IOException
   */
  @Override
  public boolean send(int code, String nickname, String args) {
    String stringCode = code + "";
    if (stringCode.length() < 2)
      stringCode = "0" + stringCode;
    if (stringCode.length() < 3)
      stringCode = "0" + stringCode;

    String message = ":" + Server.getServer().getConfig().getHostname() + " " + stringCode + " " + nickname + " " + args;
    return send(message);
  }

  /**
   * Passes arguments to send(int, String, String).
   * 
   * @param reply    The reply to be used in the message.
   * @param nickname The nickname to be used in the message.
   * @param args     The args to be used in the message.
   * @throws IOException
   */
  @Override
  public boolean send(Reply reply, String nickname, String args) {
    return send(reply.getCode(), nickname, args);
  }

  /**
   * Passes arguments to send(int, String, String).
   * 
   * @param reply  The reply to be used in the message.
   * @param client The client to be used in the message.
   * @param args   The args to be used in the message.
   * @throws IOException
   */
  @Override
  public boolean send(Reply reply, Client client, String args) {
    return send(reply.getCode(), client.getNickname(), args);
  }

  /**
   * Passes arguments to send(int, String, String).
   * 
   * @param error    The error to be used in the message.
   * @param nickname The nickname to be used in the message.
   * @param args     The args to be used in the message.
   * @throws IOException
   */
  @Override
  public boolean send(Error error, String nickname, String args) {
    return send(error.getCode(), nickname, args);
  }

  /**
   * Passes arguments to send(int, String, String).
   * 
   * @param error  The error to be used in the message.
   * @param client The client to be used in the message.
   * @param args   The args to be used in the message.
   * @throws IOException
   */
  @Override
  public boolean send(Error error, Client client, String args) {
    return send(error.getCode(), client.getNickname(), args);
  }

}
