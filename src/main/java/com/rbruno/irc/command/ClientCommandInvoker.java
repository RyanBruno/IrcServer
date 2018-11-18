package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.commands.Admin;
import com.rbruno.irc.command.commands.Away;
import com.rbruno.irc.command.commands.Info;
import com.rbruno.irc.command.commands.Invite;
import com.rbruno.irc.command.commands.Join;
import com.rbruno.irc.command.commands.Kick;
import com.rbruno.irc.command.commands.List;
import com.rbruno.irc.command.commands.Mode;
import com.rbruno.irc.command.commands.Names;
import com.rbruno.irc.command.commands.Notice;
import com.rbruno.irc.command.commands.Oper;
import com.rbruno.irc.command.commands.Part;
import com.rbruno.irc.command.commands.Ping;
import com.rbruno.irc.command.commands.Pong;
import com.rbruno.irc.command.commands.Privmsg;
import com.rbruno.irc.command.commands.Time;
import com.rbruno.irc.command.commands.Topic;
import com.rbruno.irc.command.commands.Version;
import com.rbruno.irc.command.commands.Who;
import com.rbruno.irc.command.commands.Whois;
import com.rbruno.irc.command.registration.Nick;
import com.rbruno.irc.command.registration.Quit;
import com.rbruno.irc.net.Request;

public class ClientCommandInvoker implements CommandInvoker {

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
    public void runCommand(Request request, Optional<Client> client) {
        switch (request.getCommand()) {
        case "oper":
            oper.execute(request, client);
            break;
        case "quit":
            quit.execute(request, client);
            break;
        case "join":
            join.execute(request, client);
            break;
        case "part":
            part.execute(request, client);
            break;
        case "mode":
            mode.execute(request, client);
            break;
        case "topic":
            topic.execute(request, client);
            break;
        case "names":
            names.execute(request, client);
            break;
        case "list":
            list.execute(request, client);
            break;
        case "invite":
            invite.execute(request, client);
            break;
        case "kick":
            kick.execute(request, client);
            break;
        case "version":
            version.execute(request, client);
            break;
        case "time":
            time.execute(request, client);
            break;
        case "admin":
            admin.execute(request, client);
            break;
        case "info":
            info.execute(request, client);
            break;
        case "privmsg":
            privmsg.execute(request, client);
            break;
        case "notice":
            notice.execute(request, client);
            break;
        case "who":
            who.execute(request, client);
            break;
        case "whois":
            whois.execute(request, client);
            break;
        case "ping":
            ping.execute(request, client);
            break;
        case "pong":
            pong.execute(request, client);
            break;
        case "away":
            away.execute(request, client);
            break;
        case "nick":
            nick.execute(request, client);
            break;
        default:
            // TODO
            break;
        }
    }

}
