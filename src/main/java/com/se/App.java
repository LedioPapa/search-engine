package com.se;

import com.se.command.Command;
import com.se.common.CommandType;
import com.se.common.PrintUtil;
import com.se.resolver.CommandResolver;

import java.util.Scanner;

/**
 * The class where the application cycle begins
 */
public class App {
    private final Scanner stdin = new Scanner(System.in);
    private final CommandResolver resolver = new CommandResolver();
    public void start() {
        PrintUtil.printIntro();
        PrintUtil.printCommands();
        String input = "help";
        while (!input.equals(CommandType.QUIT.getCommand())) {
            System.out.print("$ ");
            input = stdin.nextLine();
            Command command = resolver.resolve(input);
            if (command != null) {
                command.run();
            }
        }

    }
}
