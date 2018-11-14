package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public interface Connection {
  
  public Request getNextRequest(BufferedReader reader) throws Exception;
  
  public void setNickName(String nickname);

  public boolean send(byte[] message);

  public boolean send(String message);

  public boolean send(String prefix, String command, String args);
  
  public boolean send(int code, String nickname, String args);

  public boolean send(Reply reply, String nickname, String args);
  
  public boolean send(Reply reply, Client client, String args);
  
  public boolean send(Error error, String nickname, String args);

  public boolean send(Error error, Client client, String args);
}
