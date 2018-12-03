package com.rbruno.irc.command;

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
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewCommandEvent;

public class ClientCommandInvoker extends EventListener {

    private Oper oper;
    private Quit quit;
    private Join join;
    private Part part;
    private Mode mode;
    private Topic topic;
    private Names names;
    private List list;
    private Invite invite;
    private Kick kick;
    private Version version;
    private Time time;
    private Admin admin;
    private Info info;
    private Privmsg privmsg;
    private Notice notice;
    private Who who;
    private Whois whois;
    private Ping ping;
    private Pong pong;
    private Away away;
    private Nick nick;

    public ClientCommandInvoker() {
        // TODO: Nick
        nick = new Nick();
        oper = new Oper();
        quit = new Quit();
        // commands.add(new Squit());
        join = new Join();
        part = new Part();
        mode = new Mode();
        topic = new Topic();
        names = new Names();
        list = new List();
        invite = new Invite();
        kick = new Kick();
        version = new Version();
        // commands.add(new Stats());
        // commands.add(new Links());
        time = new Time();
        // commands.add(new Connect());
        // commands.add(new Trace());
        admin = new Admin();
        info = new Info();
        privmsg = new Privmsg();
        notice = new Notice();
        who = new Who();
        whois = new Whois();
        // commands.add(new Whowas());
        // commands.add(new Kill());
        ping = new Ping();
        pong = new Pong();
        // commands.add(new Error());
        // Optional Commands
        away = new Away();
    }

    @Override
    public void onNewCommand(NewCommandEvent event) {
        switch (event.getRequest().getCommand().toLowerCase()) {
        case "oper":
            oper.execute(event.getRequest(), event.getClient());
            break;
        case "quit":
            quit.execute(event.getRequest(), event.getClient());
            break;
        case "join":
            join.execute(event.getRequest(), event.getClient());
            break;
        case "part":
            part.execute(event.getRequest(), event.getClient());
            break;
        case "mode":
            mode.execute(event.getRequest(), event.getClient());
            break;
        case "topic":
            topic.execute(event.getRequest(), event.getClient());
            break;
        case "names":
            names.execute(event.getRequest(), event.getClient());
            break;
        case "list":
            list.execute(event.getRequest(), event.getClient());
            break;
        case "invite":
            invite.execute(event.getRequest(), event.getClient());
            break;
        case "kick":
            kick.execute(event.getRequest(), event.getClient());
            break;
        case "version":
            version.execute(event.getRequest(), event.getClient());
            break;
        case "time":
            time.execute(event.getRequest(), event.getClient());
            break;
        case "admin":
            admin.execute(event.getRequest(), event.getClient());
            break;
        case "info":
            info.execute(event.getRequest(), event.getClient());
            break;
        case "privmsg":
            privmsg.execute(event.getRequest(), event.getClient());
            break;
        case "notice":
            notice.execute(event.getRequest(), event.getClient());
            break;
        case "who":
            who.execute(event.getRequest(), event.getClient());
            break;
        case "whois":
            whois.execute(event.getRequest(), event.getClient());
            break;
        case "ping":
            ping.execute(event.getRequest(), event.getClient());
            break;
        case "pong":
            pong.execute(event.getRequest(), event.getClient());
            break;
        case "away":
            away.execute(event.getRequest(), event.getClient());
            break;
        case "nick":
            nick.execute(event.getRequest(), event.getClient());
            break;
        default:
            // TODO
            break;
        }

    }

}
