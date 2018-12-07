package com.rbruno.irc.events;

public abstract class EventListener {

    public void onNewRequest(NewRequestEvent event) {}

    public void onSendData(SendDataEvent event) {}
    
    public void onNickSet(NickSetEvent event) {}

    public void onConfigChanged(ConfigChangedEvent event) {}

}
