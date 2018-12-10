package com.rbruno.irc.channel;

public enum ChannelMode {
    PRIVATE(1),
    SECRETE(2),
    INVITE_ONLY(4),
    OP_MUST_SET_TOPIC(8),
    NO_OUTSIDE_MESSAGES(16),
    MODERATED(32);
    public final int code;

    private ChannelMode(int code) {
        this.code = code;
    }
}
