package com.rbruno.irc.bus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandBus<R, S, K> {
    
    Map<K, Function<R, S>> map = new HashMap<>();

    public void registerMethod(K key, Function<R, S> handler) {
        map.put(key, handler);
    }
    
    public S apply(R request, Function<R, K> key) {
        return map.get(key.apply(request)).apply(request);
    }
    
}
