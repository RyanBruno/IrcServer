package com.rbruno.irc.channel;

import java.util.Optional;

public class ChannelMode {

    private boolean privateChannel;
    private boolean secrete;
    private boolean inviteOnly;
    private boolean opMustSetTopic;
    private boolean noOutSideMessages;
    private boolean moderated;
    private int userLimit;
    private Optional<String> password;

    public boolean isPrivateChannel() {
        return privateChannel;
    }

    public void setPrivate(boolean privateChannel) {
        this.privateChannel = privateChannel;
    }

    public boolean isSecrete() {
        return secrete;
    }

    public void setSecret(boolean secrete) {
        this.secrete = secrete;
    }

    public boolean isInviteOnly() {
        return inviteOnly;
    }

    public void setInviteOnly(boolean inviteOnly) {
        this.inviteOnly = inviteOnly;
    }

    public boolean isOpMustSetTopic() {
        return opMustSetTopic;
    }

    public void setOpMustSetTopic(boolean opMustSetTopic) {
        this.opMustSetTopic = opMustSetTopic;
    }

    public boolean isNoOutsideMessages() {
        return noOutSideMessages;
    }

    public void setNoOutSideMessages(boolean noOutSideMessages) {
        this.noOutSideMessages = noOutSideMessages;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }

    public int getuserLimit() {
        return userLimit;
    }

    public void setUserLimit(int limit) {
        this.userLimit = limit;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

}
