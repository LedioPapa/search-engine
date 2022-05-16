package com.se.resolver;


import com.se.command.Command;
import com.se.command.HelpCommand;
import com.se.command.IndexCommand;
import com.se.command.QueryCommand;
import com.se.common.CommandType;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandResolverTest {

    CommandResolver resolver = new CommandResolver();

    @Test
    public void resolveUnknownCommand() {
        Command command = resolver.resolve("test");
        assertNull(command);
    }

    @Test
    public void resolveHelpCommand() {
        //help without arguments
        Command command = resolver.resolve("help");
        assertEquals(command, new HelpCommand(null));

        //help with unknown argument
        command = resolver.resolve("help dummy");
        assertEquals(command, new HelpCommand(null));

        //help with index argument
        command = resolver.resolve("help index");
        assertEquals(command, new HelpCommand(CommandType.INDEX));

        //help with query argument
        command = resolver.resolve("help query");
        assertEquals(command, new HelpCommand(CommandType.QUERY));

        //help with quit argument
        command = resolver.resolve("help quit");
        assertEquals(command, new HelpCommand(CommandType.QUIT));
    }

    @Test
    public void resolveIndexCommand() {
        //without arguments
        Command command = resolver.resolve("index");
        assertNull(command);

        //doc_id not number
        command = resolver.resolve("index l test");
        assertNull(command);

        //token contains unsupported characters
        command = resolver.resolve("index 1 test$");
        assertNull(command);

        //correct values
        command = resolver.resolve("index 1 test");
        assertEquals(command, new IndexCommand(Arrays.asList("index","1", "test")));
    }

    @Test
    public void resolveQueryCommand() {
        //without arguments
        Command command = resolver.resolve("query");
        assertNull(command);

        //unsupported characters
        command = resolver.resolve("query milk#");
        assertNull(command);

        //correct values
        command = resolver.resolve("query milk&honey");
        assertEquals(command, new QueryCommand("milk AND honey"));
    }
}