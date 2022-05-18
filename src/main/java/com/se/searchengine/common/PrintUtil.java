package com.se.searchengine.common;

import java.util.List;
import java.util.Locale;

import static java.text.MessageFormat.format;
import static java.util.ResourceBundle.getBundle;

/**
 * Util to print different messages to stdout
 */
public class PrintUtil {

    /**
     * print command list
     */
    public static void printCommands() {
        System.out.println(getBundle("messages", Locale.US).getString("commands"));
    }

    /**
     * print helpful information about index command
     */
    public static void indexHelp() {
        System.out.println(getBundle("messages", Locale.US).getString("helpIndex"));
    }

    /**
     * print helpful information about query command
     */
    public static void queryHelp() {
        System.out.println(getBundle("messages", Locale.US).getString("queryHelp"));
    }

    /**
     * print helpful information about quit command
     */
    public static void quitHelp() {
        System.out.println(getBundle("messages", Locale.US).getString("quitHelp"));
    }

    /**
     * print message indicating document was added successfully to the index
     * @param duration
     */
    public static void indexOk(long duration) {
        System.out.println(format(getBundle("messages", Locale.US).getString("indexOk"), duration));
    }

    /**
     * print results from querying the application
     * @param result
     * @param duration
     */
    public static void queryResult(List<Long> result, long duration) {
        System.out.println(format(getBundle("messages", Locale.US).getString("queryResult"),
                result.toString()
                        .replace("[","")
                        .replace("]","")
                        .replace(",",""), duration));
    }

    /**
     * print message telling an unknown command was given
     * @param command
     */
    public static void unknownCommand(String command) {
        System.out.println(format(getBundle("messages", Locale.US).getString("unknownCommand"), command));
    }

    /**
     * print message telling there was an error running a command
     * @param command
     * @param message
     */
    public static void commandError(String command, String message) {
        System.out.println(format(getBundle("messages", Locale.US).getString("commandError"), command, message));
    }

}
