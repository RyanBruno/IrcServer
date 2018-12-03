package com.rbruno.irc.command.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.rbruno.irc.Server;
import com.rbruno.irc.TestBootstrap;
import com.rbruno.irc.TestConnection;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.LocalChannel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.net.Request;

public class TestCommands {

    Server server;

    @Before
    public void before() throws Exception {
        server = new Server(new TestBootstrap());
    }

    @Test
    public void testAdmin() {
        TestConnection connection = new TestConnection();
        Client client = new LocalClient(connection, "nick", "user", "host", "server", "real");
        Request request = new Request(connection, "admin");
        server.getClientCommandInvoker().runCommand(request, Optional.of(client));

        assertFalse(connection.closed.isPresent());

        assertTrue(connection.sentLines.get(0).contains("256 nick localhost :Administrative info"));
        assertTrue(connection.sentLines.get(1).contains("257 nick localhost :Ryan Bruno"));
        assertTrue(connection.sentLines.get(2).contains("258 nick localhost :JRoll506"));

    }

    @Test
    public void testAway() {
        TestConnection connection = new TestConnection();
        Client client = new LocalClient(connection, "nick", "user", "host", "server", "real");

        Request request = new Request(connection, "away :Away");
        server.getClientCommandInvoker().runCommand(request, Optional.of(client));

        assertFalse(connection.closed.isPresent());
        assertTrue(client.getAwayMessage().isPresent());
        assertTrue(connection.sentLines.get(0).contains("306 nick :You have been marked as being away"));

        Request request2 = new Request(connection, "away");
        server.getClientCommandInvoker().runCommand(request2, Optional.of(client));

        assertFalse(connection.closed.isPresent());
        assertFalse(client.getAwayMessage().isPresent());
        assertTrue(connection.sentLines.get(1).contains("305 nick :You are no longer marked as being away"));
    }

    @Test
    public void testInfo() {
        TestConnection connection = new TestConnection();
        Client client = new LocalClient(connection, "nick", "user", "host", "server", "real");

        Request request = new Request(connection, "info");
        server.getClientCommandInvoker().runCommand(request, Optional.of(client));

        assertFalse(connection.closed.isPresent());
        assertTrue(connection.sentLines.get(0).contains("371 nick :RBruno's IRCd (v1.0-RELEASE)"));
        assertTrue(connection.sentLines.get(1).contains("371 nick :Developer: Ryan Bruno <ryanbruno506@gmail.com>"));
    }

    @Test
    public void testInvite() {
        TestConnection connection = new TestConnection();
        Client client = new LocalClient(connection, "nick", "user", "host", "server", "real");

        Request request = new Request(connection, "info");
        server.getClientCommandInvoker().runCommand(request, Optional.of(client));

        // TODO
    }

    @Test
    public void testJoin() {
        TestConnection connection = new TestConnection();
        Client client = new LocalClient(connection, "nick", "user", "host", "server", "real");

        Request request = new Request(connection, "join #test");
        server.getClientCommandInvoker().runCommand(request, Optional.of(client));

        assertTrue(connection.sentLines.get(0).contains("JOIN #test"));
        assertTrue(connection.sentLines.get(1).contains("353 nick @ #test :nick "));
        assertTrue(connection.sentLines.get(2).contains("366 nick #test :End of /NAMES list"));
        assertTrue(connection.sentLines.get(3).contains("332 nick #test :Default Topic"));
    }

    @Test
    public void testKick() {
        TestConnection connection = new TestConnection();
        Client client = new LocalClient(connection, "nick", "user", "host", "server", "real");
        server.getClientManager().addClient(client);
        Channel channel = new LocalChannel("#test", null);
        server.getChannelManger().addChannel(channel);
        channel.addClient(client);
        channel.setChanOp(client, true);
        
        Request request = new Request(connection, "kick #test nick");
        server.getClientCommandInvoker().runCommand(request, Optional.of(client));

        assertTrue(connection.sentLines.get(4).contains(":nick!user@host KICK #test nick:You have been kicked from the channel"));
    }

}
