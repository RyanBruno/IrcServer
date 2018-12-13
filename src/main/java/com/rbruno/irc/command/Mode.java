package com.rbruno.irc.command;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Mode extends Command {

    public Mode() {
        super("MODE", 1);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        if (request.getArgs()[0].startsWith("#") || request.getArgs()[0].startsWith("#")) {
            Channel channel = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);

            if (channel == null) {
                request.getConnection().send(Error.ERR_NOSUCHCHANNEL, client.get(), request.getArgs()[0] + " :No such channel");
                return;
            }

            if (request.getArgs().length <= 1) {
                request.getConnection().send(Reply.RPL_CHANNELMODEIS, client.get(), channel.getName() + " +"/* TODO */);
                return;
            }

            if (!channel.isChanOp(client.get()) && !Server.getServer().getOperManager().isop(client.get())) {
                request.getConnection().send(Error.ERR_NOPRIVILEGES, client.get(), ":Permission Denied- You're not an IRC operator");
                return;
            }

            boolean add = true;

            for (char c : request.getArgs()[1].toLowerCase().toCharArray()) {
                switch (c) {
                case '+':
                    add = true;
                    break;
                case '-':
                    add = false;
                    break;
                case 'o':
                    Client target = null;

                    if (request.getArgs().length >= 3) {
                        target = Server.getServer().getClientManager().getClient(request.getArgs()[2]);
                    }

                    if (target == null) {
                        request.getConnection().send(Error.ERR_NOSUCHNICK, client.get(), request.getArgs()[2] + " :No such nick");
                        continue;
                    }

                    channel.setChanOp(target, add);
                    break;
                case 'p':
                    channel.getModes().setPrivate(add);
                    break;
                case 's':
                    channel.getModes().setSecret(add);
                    break;
                case 'i':
                    channel.getModes().setInviteOnly(add);
                    break;
                case 't':
                    channel.getModes().setOpMustSetTopic(add);
                    break;
                case 'n':
                    channel.getModes().setNoOutSideMessages(add);
                    break;
                case 'm':
                    channel.getModes().setModerated(add);
                    break;
                case 'l':
                    if (request.getArgs().length < 3) {
                        request.getConnection().send(Error.ERR_NEEDMOREPARAMS, client.get(), request.getCommand() + " :Not enough parameters");
                        continue;
                    }
                    try {
                        channel.getModes().setUserLimit(Integer.parseInt(request.getArgs()[2]));
                    } catch (NumberFormatException e) {
                        request.getConnection().send(Error.ERR_NEEDMOREPARAMS, client.get(), request.getCommand() + " :Not enough parameters");
                    }
                    break;
                case 'b':
                    Client banee = null;

                    if (request.getArgs().length >= 3) {
                        banee = Server.getServer().getClientManager().getClient(request.getArgs()[2]);
                    }

                    if (banee == null) {
                        request.getConnection().send(Error.ERR_NOSUCHNICK, client.get(), request.getArgs()[2] + " :No such nick");
                        continue;
                    }

                    channel.setBanned(banee, add);
                    break;
                case 'v':
                    Client voicee = null;

                    if (request.getArgs().length >= 3) {
                        voicee = Server.getServer().getClientManager().getClient(request.getArgs()[2]);
                    }

                    if (voicee == null) {
                        request.getConnection().send(Error.ERR_NOSUCHNICK, client.get(), request.getArgs()[2] + " :No such nick");
                        continue;
                    }

                    channel.setBanned(voicee, add);
                    break;
                case 'k':
                    if (add) {
                        if (request.getArgs().length < 3) {
                            request.getConnection().send(Error.ERR_NEEDMOREPARAMS, client.get(), request.getCommand() + " :Not enough parameters");
                            continue;
                        }
                        channel.getModes().setPassword(Optional.of(request.getArgs()[2]));
                    } else {
                        channel.getModes().setPassword(Optional.empty());
                    }
                    break;
                }
            }

        } else {
            // Client
        }

    }

}
