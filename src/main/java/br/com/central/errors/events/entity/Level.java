package br.com.central.errors.events.entity;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Level {
    ERROR("ERROR"), WARNING("WARNING"), INFO("INFO");

    private String name;

    Level(String name) {
        this.name = name;
    }

    public static Level of(String level) {
        return Stream.of(Level.values())
                .filter(p -> p.getName() == level)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
