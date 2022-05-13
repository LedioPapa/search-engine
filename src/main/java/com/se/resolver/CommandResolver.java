package com.se.resolver;

import com.se.command.HelpCommand;
import com.se.command.IndexCommand;
import com.se.command.QueryCommand;
import com.se.common.CommandType;
import com.se.common.PrintUtil;
import com.se.command.Command;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains logic to resolve user input
 * from String to a Command
 */
public class CommandResolver {

    /**
     * Resolves the command type and calls the
     * respective resolver methods
     * @param input
     * @return Command or null
     */
    public Command resolve(String input) {
        List<String> commandParts = Stream.of(input.trim().split(" "))
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toList());
        if (commandParts.size() > 0) {
            String commandHead = commandParts.get(0);
            Optional<CommandType> command = Stream.of(CommandType.values())
                    .filter(commandType -> commandType.getCommand().equals(commandHead))
                    .findFirst();
            if (command.isPresent()) {
                switch (command.get()) {
                    case HELP:
                        return resolveHelp(commandParts);
                    case QUIT:
                        System.exit(0);
                    case INDEX:
                        return resolveIndex(commandParts);
                    case QUERY:
                        return resolveQuery(commandParts);
                    default:
                        break;
                }
            } else {
                PrintUtil.unknownCommand(commandHead);
            }
        }
        return null;
    }

    /**
     * Contains logic to resolve what help is required
     * @param commandParts
     * @return HelpCommand
     */
    private Command resolveHelp(List<String> commandParts) {
        if (commandParts.size() == 1) {
            return new HelpCommand(null);
        } else {
            Optional<CommandType> command = Stream.of(CommandType.values())
                    .filter(commandType -> commandType.getCommand().equals(commandParts.get(1)))
                    .findFirst();
            if (!command.isPresent()) {
                return new HelpCommand(null);
            }
            switch (command.get()) {
                case INDEX:
                    return new HelpCommand(CommandType.INDEX);
                case QUERY:
                    return new HelpCommand(CommandType.QUERY);
                case QUIT:
                    return new HelpCommand(CommandType.QUIT);
                default:
                    return new HelpCommand(null);
            }
        }
    }

    /**
     * Resolves and validates the index command input
     * @param commandParts
     * @return IndexCommand or null
     */
    private Command resolveIndex(List<String> commandParts) {
        if (commandParts.size() < 3) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), "arguments mismatch! required arguments: <doc_id> and at least one <token>");
            return null;
        }
        if (!Pattern.matches("[0-9]+", commandParts.get(1))) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), String.format("unsupported argument %s! <document id> must be a number", commandParts.get(1)));
            return null;
        }
        Optional<String> unsupportedTokenFormat = commandParts
                .stream()
                .skip(2)
                .filter(arg -> !Pattern.matches("[a-zA-Z0-9]*", arg))
                .findFirst();
        if (unsupportedTokenFormat.isPresent()) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), String.format("unsupported argument %s! All <token> must be of alphanumeric value", unsupportedTokenFormat.get()));
            return null;
        }

        return new IndexCommand(commandParts);
    }

    /**
     * Resolves and validates the query command input
     * @param commandParts
     * @return QueryCommand or null
     */
    private Command resolveQuery(List<String> commandParts) {
        if (commandParts.size() < 2) {
            PrintUtil.commandError(CommandType.QUERY.getCommand(), "argument <expression> missing. See 'help query' for more details.");
            return null;
        }
        String expression = String.join(" ", commandParts.subList(1, commandParts.size()))
                .replace("&", " AND ")
                .replace("|", " OR ");
        if (!Pattern.matches("[a-zA-Z0-9()\\s*]*", expression)) {
            PrintUtil.commandError(CommandType.QUERY.getCommand(), "unsupported characters found in <expression>. Allowed characters are: alphanumeric and special characters & | ()");
            return null;
        }

        return new QueryCommand(expression);
    }
}
