package com.rbruno.irc.bus;

import java.util.function.Function;

public interface Bus<R, S, K> {

    public void forwardTo(K key, Function<R, S> responder);

    public S apply(K key, R request);
}
