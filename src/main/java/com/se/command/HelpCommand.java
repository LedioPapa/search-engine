package com.se.command;

import com.se.common.CommandType;
import com.se.common.PrintUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Prints different help messages to stdout
 */
@EqualsAndHashCode
@AllArgsConstructor
public class HelpCommand implements Command{
    private final CommandType commandType;

    /**
     * Defines the command type and prints the output accordingly
     * Otherwise prints the command list
     */
    @Override
    public void run() {
        if (commandType != null) {
            switch (commandType) {
                case INDEX:
                    PrintUtil.indexHelp();
                    break;
                case QUERY:
                    PrintUtil.queryHelp();
                    break;
                case QUIT:
                    PrintUtil.quitHelp();
                    break;
                default:
                    PrintUtil.printCommands();
                    break;
            }
        } else {
            System.out.println("No valid argument found! Printing Available Commands.");
            PrintUtil.printCommands();
        }
    }
}
