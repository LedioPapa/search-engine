package com.se.searchengine;

import com.se.searchengine.common.CommandType;
import com.se.searchengine.common.PrintUtil;
import com.se.searchengine.resolver.CommandResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {
    private final Scanner stdin = new Scanner(System.in);

    private CommandResolver resolver;

    @Override
    public void run(String... args) throws Exception {
        PrintUtil.printCommands();
        String input = "help";
        while (!input.equals(CommandType.QUIT.getCommand())) {
            System.out.print("$ ");
            input = stdin.nextLine();
            resolver.resolve(input);
        }
        System.exit(0);
     }

    @Autowired
    public void setResolver(CommandResolver resolver) {
        this.resolver = resolver;
    }
}