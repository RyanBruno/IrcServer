package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.client.Admin;
import com.rbruno.irc.command.client.Away;
import com.rbruno.irc.command.client.Info;
import com.rbruno.irc.command.client.Invite;
import com.rbruno.irc.command.client.Join;
import com.rbruno.irc.command.client.Kick;
import com.rbruno.irc.command.client.List;
import com.rbruno.irc.command.client.Mode;
import com.rbruno.irc.command.client.Names;
import com.rbruno.irc.command.client.Nick;
import com.rbruno.irc.command.client.Notice;
import com.rbruno.irc.command.client.Oper;
import com.rbruno.irc.command.client.Part;
import com.rbruno.irc.command.client.Ping;
import com.rbruno.irc.command.client.Pong;
import com.rbruno.irc.command.client.Privmsg;
import com.rbruno.irc.command.client.Quit;
import com.rbruno.irc.command.client.Time;
import com.rbruno.irc.command.client.Topic;
import com.rbruno.irc.command.client.Version;
import com.rbruno.irc.command.client.Who;
import com.rbruno.irc.command.client.Whois;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.NewRequestEvent;

public class ClientCommandInvoker implements Listener {

    private ClientCommand oper;
    private ClientCommand quit;
    private ClientCommand join;
    private ClientCommand part;
    private ClientCommand mode;
    private ClientCommand topic;
    private ClientCommand names;
    private ClientCommand list;
    private ClientCommand invite;
    private ClientCommand kick;
    private ClientCommand version;
    private ClientCommand time;
    private ClientCommand admin;
    private ClientCommand info;
    private ClientCommand privmsg;
    private ClientCommand notice;
    private ClientCommand who;
    private ClientCommand whois;
    private ClientCommand ping;
    private ClientCommand pong;
    private ClientCommand away;
    private ClientCommand nick;

    private CommandModule commandModule;
    
    public ClientCommandInvoker(CommandModule commandModule) {
        // TODO: Nick
        nick = new Nick(commandModule);
        oper = new Oper(commandModule);
        quit = new Quit(commandModule);
        // commands.add(new Squit());
        join = new Join(commandModule);
        part = new Part(commandModule);
        mode = new Mode(commandModule);
        topic = new Topic(commandModule);
        names = new Names(commandModule);
        list = new List(commandModule);
        invite = new Invite(commandModule);
        kick = new Kick(commandModule);
        version = new Version(commandModule);
        // commands.add(new Stats());
        // commands.add(new Links());
        time = new Time()commandModule;
        // commands.add(new Connect());
        // commands.add(new Trace());
        admin = new Admin(commandModule);
        info = new Info(commandModule);
        privmsg = new Privmsg(commandModule);
        notice = new Notice(commandModule);
        who = new Who(commandModule);
        whois = new Whois(commandModule);
        // commands.add(new Whowas());
        // commands.add(new Kill());
        ping = new Ping(commandModule);
        pong = new Pong(commandModule);
        // commands.add(new Error());
        // Optional Commands
        away = new Away(commandModule);
    }

    @EventListener
    public void onNewRequest(NewRequestEvent event) {
        Client client = commandModule.getClient(event.getRequest().getSocketChannel());

        switch (event.getRequest().getCommand().toLowerCase()) {
        case "oper":
            oper.execute(event.getRequest(), client);
            break;
        case "quit":
            quit.execute(event.getRequest(), client);
            break;
        case "join":
            join.execute(event.getRequest(), client);
            break;
        case "part":
            part.execute(event.getRequest(), client);
            break;
        case "mode":
            mode.execute(event.getRequest(), client);
            break;
        case "topic":
            topic.execute(event.getRequest(), client);
            break;
        case "names":
            names.execute(event.getRequest(), client);
            break;
        case "list":
            list.execute(event.getRequest(), client);
            break;
        case "invite":
            invite.execute(event.getRequest(), client);
            break;
        case "kick":
            kick.execute(event.getRequest(), client);
            break;
        case "version":
            version.execute(event.getRequest(), client);
            break;
        case "time":
            time.execute(event.getRequest(), client);
            break;
        case "admin":
            admin.execute(event.getRequest(), client);
            break;
        case "info":
            info.execute(event.getRequest(), client);
            break;
        case "privmsg":
            privmsg.execute(event.getRequest(), client);
            break;
        case "notice":
            notice.execute(event.getRequest(), client);
            break;
        case "who":
            who.execute(event.getRequest(), client);
            break;
        case "whois":
            whois.execute(event.getRequest(), client);
            break;
        case "ping":
            ping.execute(event.getRequest(), client);
            break;
        case "pong":
            pong.execute(event.getRequest(), client);
            break;
        case "away":
            away.execute(event.getRequest(), client);
            break;
        case "nick":
            nick.execute(event.getRequest(), client);
            break;
        default:
            // TODO
            break;
        }

    }

}
