package com.se.searchengine.common;

import java.util.List;

/**
 * Util to print different messages to stdout
 */
public class PrintUtil {
    public static void printIntro() {
        System.out.println("Search Engine Concept");
    }
    public static void printCommands() {
        System.out.println("Commands:\n" +
                "\t$ help [command_name]\n" +
                "\t$ index <doc_id> <token1> <token2> ... <tokenN>\n" +
                "\t$ query <expression>\n" +
                "\t$ quit");
    }

    public static void indexHelp() {
        System.out.println("USAGE:\n" +
                "index <doc_id> <token1> <token2> ... <tokenN>\n" +
                "\n" +
                "Adds a document to the index.\n" +
                "\n" +
                "<doc-id> is an integer.\n" +
                "<token> are non unique alphanumeric strings.\n" +
                "<doc_id> and at least one <token> are required.\n" +
                "If <doc_id> is already used, it will be replaced.\n" +
                "\n" +
                "Examples:\n" +
                "index 1 soup tomato cream salt\n" +
                "index 2 cake sugar eggs flour sugar cocoa cream butter\n" +
                "index 1 bread butter salt\n" +
                "index 3 soup fish potato salt pepper");
    }

    public static void queryHelp() {
        System.out.println("USAGE:\n" +
                "query <expression>\n" +
                "\n" +
                "Returnd a list of the documents that contain the requested data from <expression>\n" +
                "\n" +
                "<expression> is an arbitrary expression composed of alphanumeric tokens and the special symbols &, |, (, and ).\n" +
                "\n" +
                "Examples:\n" +
                "query butter\n" +
                "query sugar\n" +
                "query soup\n" +
                "query (butter | potato) & salt");
    }

    public static void quitHelp() {
        System.out.println("USAGE:\n" +
                "quit\n" +
                "\n" +
                "Interrupt everything and stop the Application.");
    }

    public static void indexOk(long duration) {
        System.out.printf("index ok Operation lasted %s ms\n", duration);
    }

    public static void queryResult(List<Long> result, long duration) {
        System.out.printf("query results %s\nOperation lasted %s ms\n", result.toString(), duration);
    }

    public static void unknownCommand(String command) {
        System.out.printf("Unknown command %s%nUse command 'help' to view a list of available commands!%n", command);
    }

    public static void commandError(String command, String message) {
        System.out.printf("%s error %s%n", command, message);
    }

}
