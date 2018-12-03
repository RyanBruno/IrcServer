package com.rbruno.irc.command;

import com.rbruno.irc.command.commands.Admin;
import com.rbruno.irc.command.commands.Away;
import com.rbruno.irc.command.commands.Info;
import com.rbruno.irc.command.commands.Invite;
import com.rbruno.irc.command.commands.Join;
import com.rbruno.irc.command.commands.Kick;
import com.rbruno.irc.command.commands.List;
import com.rbruno.irc.command.commands.Mode;
import com.rbruno.irc.command.commands.Names;
import com.rbruno.irc.command.commands.Nick;
import com.rbruno.irc.command.commands.Notice;
import com.rbruno.irc.command.commands.Oper;
import com.rbruno.irc.command.commands.Part;
import com.rbruno.irc.command.commands.Ping;
import com.rbruno.irc.command.commands.Pong;
import com.rbruno.irc.command.commands.Privmsg;
import com.rbruno.irc.command.commands.Quit;
import com.rbruno.irc.command.commands.Time;
import com.rbruno.irc.command.commands.Topic;
import com.rbruno.irc.command.commands.Version;
import com.rbruno.irc.command.commands.Who;
import com.rbruno.irc.command.commands.Whois;
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
