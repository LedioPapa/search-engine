package com.se.searchengine.common;

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
