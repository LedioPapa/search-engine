package com.se.searchengine.common;

/**
 * Enum to help use types of commands throughout the application
 */
public enum CommandType {
    HELP("help"),INDEX("index"), QUERY("query"), QUIT("quit");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
