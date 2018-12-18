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
import com.rbruno.irc.command.commands.Pass;
import com.rbruno.irc.command.commands.Ping;
import com.rbruno.irc.command.commands.Pong;
import com.rbruno.irc.command.commands.Privmsg;
import com.rbruno.irc.command.commands.Quit;
import com.rbruno.irc.command.commands.Time;
import com.rbruno.irc.command.commands.Topic;
import com.rbruno.irc.command.commands.User;
import com.rbruno.irc.command.commands.Version;
import com.rbruno.irc.command.commands.Who;
import com.rbruno.irc.command.commands.Whois;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public class CommandInvoker {

	private Pass pass;
	private Nick nick;
	private User user;

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

	private CommandContext context;

	public CommandInvoker(CommandContext context) {
		this.context = context;

		pass = new Pass(context);
		nick = new Nick(context);
		user = new User(context);

		nick = new Nick(context);
		oper = new Oper(context);
		quit = new Quit(context);
		// commands.add(new Squit());
		join = new Join(context);
		part = new Part(context);
		mode = new Mode(context);
		topic = new Topic(context);
		names = new Names(context);
		list = new List(context);
		invite = new Invite(context);
		kick = new Kick(context);
		version = new Version(context);
		// commands.add(new Stats());
		// commands.add(new Links());
		time = new Time(context);
		// commands.add(new Connect());
		// commands.add(new Trace());
		admin = new Admin(context);
		info = new Info(context);
		privmsg = new Privmsg(context);
		notice = new Notice(context);
		who = new Who(context);
		whois = new Whois(context);
		// commands.add(new Whowas());
		// commands.add(new Kill());
		ping = new Ping(context);
		pong = new Pong(context);
		// commands.add(new Error());
		// Optional Commands
		away = new Away(context);
	}

	public Response[] runCommand(Request request) {
		switch (request.getCommand()) {
		case "pass":
			return pass.execute(request);
		case "nick":
			return nick.execute(request);
		case "user":
			return user.execute(request);
		case "oper":
			return oper.execute(request);
		case "quit":
			return quit.execute(request);
		case "join":
			return join.execute(request);
		case "part":
			return part.execute(request);
		case "mode":
			return mode.execute(request);
		case "topic":
			return topic.execute(request);
		case "names":
			return names.execute(request);
		case "list":
			return list.execute(request);
		case "invite":
			return invite.execute(request);
		case "kick":
			return kick.execute(request);
		case "version":
			return version.execute(request);
		case "time":
			return time.execute(request);
		case "admin":
			return admin.execute(request);
		case "info":
			return info.execute(request);
		case "privmsg":
			return privmsg.execute(request);
		case "notice":
			return notice.execute(request);
		case "who":
			return who.execute(request);
		case "whois":
			return whois.execute(request);
		case "ping":
			return ping.execute(request);
		case "pong":
			return pong.execute(request);
		case "away":
			return away.execute(request);
		default:
			return new Response[0]; // TODO return no such command
		}
	}

}
