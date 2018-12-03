package com.rbruno.irc.events;

public abstract class EventListener {

    public void onServerOpen(ServerOpenEvent event) {}

    public void onNewConnection(NewConnectionEvent event) {}

    public void onNewRequest(NewRequestEvent event) {}

    public void onSendData(SendDataEvent event) {}
    
    public void onNickSet(NickSetEvent event) {}

    public void onConfigChanged(ConfigChangedEvent event) {}

    public void onClientRegistered(ClientRegisteredEvent event) {}
}
