package com.rbruno.irc.config;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class StreamConfig implements Config {
  
  private int port;
  private String hostname;

  public void load(Stream<String> stream) {
    stream = stream.filter(l -> l.startsWith("#") ? false : true);
    stream = stream.filter(l -> l.split("=").length == 2 ? true : false);
    Optional<String> port = stream.filter(l -> l.startsWith("port")).findFirst();
    Optional<String> hostname = stream.filter(l -> l.startsWith("hostname")).findFirst();
    
    if (!port.isPresent() || !hostname.isPresent()) {
      // TODO LOG & Error handling
      new Exception("Port and Hostname is required!").printStackTrace();
      return;
    }
    //TODO
    this.port = Integer.parseInt(port.get().split("=")[1]);
    this.hostname = hostname.get();
  }
  
  @Override
  public int getPort() {
    return port;
  }

  @Override
  public String getHostname() {
    return hostname;
  }

}
