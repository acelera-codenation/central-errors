package br.com.central.errors.events.entity.enums;

public enum Level {

    ERROR("ERROR"), WARNING("WARNING"), INFO("INFO");

    private String name;

    Level(String name) {
        this.name = name;
    }

    public static Level of(String level) {
        return Enum.valueOf(Level.class, level);
    }
}
