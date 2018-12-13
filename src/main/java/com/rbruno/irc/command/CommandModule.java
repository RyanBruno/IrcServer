package com.rbruno.irc.command;

import com.rbruno.irc.bus.CommandBus;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.Module;

public class CommandModule extends Module {

    public CommandModule(EventDispacher eventDispacher, CommandBus bus) {
        super(eventDispacher, bus);
    }

    @Override
    public void registerEventListeners() {
    }

    @Override
    public void setForwards() {
        getBus().forwardTo("NICK", new Nick(this));
        getEventDispacher().registerListener(new Oper(this));
        getEventDispacher().registerListener(new Quit(this));
        // commands.add(new Squit());
        getBus().forwardTo("JOIN", new Join(this));
        getEventDispacher().registerListener(new Part(this));
        getEventDispacher().registerListener(new Mode(this));
        getEventDispacher().registerListener(new Topic(this));
        getEventDispacher().registerListener(new Names(this));
        getEventDispacher().registerListener(new List(this));
        getEventDispacher().registerListener(new Invite(this));
        getBus().forwardTo("KICK", new Kick(this));
        getEventDispacher().registerListener(new Version(this));
        // commands.add(new Stats());
        // commands.add(new Links());
        getEventDispacher().registerListener(new Time(this));
        // commands.add(new Connect());
        // commands.add(new Trace());
        getBus().forwardTo("ADMIN", new Admin(this));
        getBus().forwardTo("INFO", new Info(this));
        getEventDispacher().registerListener(new Privmsg(this));
        getEventDispacher().registerListener(new Notice(this));
        getEventDispacher().registerListener(new Who(this));
        getEventDispacher().registerListener(new Whois(this));
        // commands.add(new Whowas());
        // commands.add(new Kill());
        getEventDispacher().registerListener(new Ping(this));
        // getEventDispacher().registerListener(new Pong(this));
        // commands.add(new Error());
        // Optional Commands
        getBus().forwardTo("AWAY", new Away(this));
    }

    public Config getConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    public Client getClient(io.netty.channel.Channel channel) {
        // TODO Auto-generated method stub
        return null;
    }

    public Channel getChannel(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public Client getClient(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isNickInUse(String string) {
        // TODO Auto-generated method stub
        return false;
    }

}
