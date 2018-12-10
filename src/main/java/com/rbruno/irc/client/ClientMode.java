package com.rbruno.irc.client;

public enum ClientMode {
    INVISIBLE(1), NOTICE(2), WALLOPS(4), OPERATOR(8);
    public final int code;

    private ClientMode(int code) {
        this.code = code;
    }
}
