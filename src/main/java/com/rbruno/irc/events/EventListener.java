package com.rbruno.irc.events;

public abstract class EventListener {

    public void onServerOpen(ServerOpenEvent event) {}

    public void onNewConnection(NewConnectionEvent event) {}

    public void onNewLine(NewLineEvent event) {}

    public void onNewRequest(NewRequestEvent event) {}

    public void onNewCommand(NewCommandEvent event) {}
}
