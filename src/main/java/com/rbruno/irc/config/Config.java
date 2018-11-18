package com.rbruno.irc.config;

import java.util.Optional;

public interface Config {
  
  public int getPort();
  public String getHostname();
  
  public Optional<String> getAdminLoc1();
  public Optional<String> getAdminLoc2();
  public Optional<String> getAdminMail();


}
