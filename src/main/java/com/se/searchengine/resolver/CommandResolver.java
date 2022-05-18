package com.se.searchengine.resolver;

import com.se.searchengine.common.CommandType;
import com.se.searchengine.common.PrintUtil;
import com.se.searchengine.service.SearchService;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static java.util.ResourceBundle.getBundle;

/**
 * Contains logic to validate and resolve user input
 * to call the appropriate service method
 */
@Component
@NoArgsConstructor
public class CommandResolver {

    private SearchService searchService;

    /**
     * Resolves the command type and calls the
     * respective resolver methods
     * @param input
     */
    public void resolve(String input) {
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
                        resolveHelp(commandParts);
                        break;
                    case QUIT:
                        System.exit(0);
                    case INDEX:
                        resolveIndex(commandParts);
                        break;
                    case QUERY:
                        resolveQuery(commandParts);
                        break;
                    default:
                        break;
                }
            } else {
                PrintUtil.unknownCommand(commandHead);
            }
        }
    }

    /**
     * Contains logic to resolve what help is required
     * and calls the service
     * @param commandParts
     */
    private void resolveHelp(List<String> commandParts) {
        if (commandParts.size() == 1) {
            searchService.help(null);
            return;
        } else {
            Optional<CommandType> command = Stream.of(CommandType.values())
                    .filter(commandType -> commandType.getCommand().equals(commandParts.get(1)))
                    .findFirst();
            if (!command.isPresent()) {
                searchService.help(null);
                return;
            }
            switch (command.get()) {
                case INDEX:
                    searchService.help(CommandType.INDEX);
                    break;
                case QUERY:
                    searchService.help(CommandType.QUERY);
                    break;
                case QUIT:
                    searchService.help(CommandType.QUIT);
                    break;
                default:
                    searchService.help(null);
                    break;
            }
        }
    }

    /**
     * Validates and resolves the index command input
     * and calls the service
     * @param commandParts
     */
    private void resolveIndex(List<String> commandParts) {
        if (commandParts.size() < 3) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), getBundle("messages", Locale.US).getString("indexArgMismatch"));
            return;
        }
        if (!Pattern.matches("[0-9]+", commandParts.get(1))) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), format(getBundle("messages", Locale.US).getString("indexUnsupportedArgDocId"), commandParts.get(1)));
            return;
        }
        Optional<String> unsupportedTokenFormat = commandParts
                .stream()
                .skip(2)
                .filter(arg -> !Pattern.matches("[a-zA-Z0-9]*", arg))
                .findFirst();
        if (unsupportedTokenFormat.isPresent()) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), format(getBundle("messages", Locale.US).getString("indexUnsupportedArgToken"), unsupportedTokenFormat.get()));
            return;
        }

        searchService.index(commandParts);
    }

    /**
     * Validates and resolves the query command input
     * and calls the service
     * @param commandParts
     */
    private void resolveQuery(List<String> commandParts) {
        if (commandParts.size() < 2) {
            PrintUtil.commandError(CommandType.QUERY.getCommand(), getBundle("messages",Locale.US).getString("queryArgMissing"));
            return;
        }
        String expression = String.join(" ", commandParts.subList(1, commandParts.size()))
                .replace("&", " AND ")
                .replace("|", " OR ");
        if (!Pattern.matches("[a-zA-Z0-9()\\s*]*", expression)) {
            PrintUtil.commandError(CommandType.QUERY.getCommand(), getBundle("messages",Locale.US).getString("queryUnsupportedChars"));
            return;
        }

        searchService.query(expression);
    }

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
