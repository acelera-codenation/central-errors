package br.com.central.errors.events.entity;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Level {
    ERROR("ERROR"), WARNING("WARNING"), INFO("INFO");

    private String level;

    Level(String level) {
        this.level = level;
    }

    public static Level of(String level) {
        return Stream.of(Level.values())
                .filter(p -> p.getLevel() == level)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
