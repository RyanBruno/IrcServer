package com.rbruno.irc.net;

import java.util.Map;
import java.util.Map.Entry;

import com.rbruno.irc.bus.CommandBus;
import com.rbruno.irc.client.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RequestChannelHandler extends SimpleChannelInboundHandler<String> {

    private CommandBus bus;
    
    public RequestChannelHandler(CommandBus bus) {
        this.bus = bus;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Request request = new Request(ctx.channel(), msg);
        Response response = bus.apply(request.getCommand(), request);

        Map<Client[], String> map = response.getResponseMap();
        for (Entry<Client[], String> entry : map.entrySet()) {
            if (entry.getKey().length <= 0) {
                ctx.writeAndFlush(entry.getValue());
            } else {
                for (Client client : entry.getKey()) {
                    client.getChannel().writeAndFlush(entry.getValue());
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
