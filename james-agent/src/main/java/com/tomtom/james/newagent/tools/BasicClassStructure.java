package com.tomtom.james.newagent.tools;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BasicClassStructure implements ClassStructure {
    // map className -> set of Class in any classloader
    private Map<String, Set<Class>> container = new ConcurrentHashMap<>();

    public boolean contains(String className) {
        return container.containsKey(className);
    }

    @Override
    public Set<Class> getChildren(String className) {
        return (container.get(className) != null) ? container.get(className) : new HashSet<>();
    }

    // FIXME - if you want to implement multithreaded classScanner there is the point that multithreading pitfall is waiting for you - it's not threadsafe !!!!!
    @Override
    public void addChild(String className, Class<?> clazz) {
        container.computeIfAbsent(className, key -> new HashSet<>());
        Set<Class> classSet = container.get(className);
        classSet.add(clazz);
        container.put(className, classSet);
    }

    @Override
    public Map<String, Set<Class>> getMap() {
        return container;
    }

    public Collection<Set<Class>> values() {
        return container.values();
    }

}
