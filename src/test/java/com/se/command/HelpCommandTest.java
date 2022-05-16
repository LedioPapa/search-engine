package com.se.command;


import com.se.common.CommandType;
import com.se.common.PrintUtil;
import org.junit.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

public class HelpCommandTest {

    @Test
    public void helpCommand() {
        MockedStatic<PrintUtil> mockedUtil = mockStatic(PrintUtil.class);

        //CommandType is null
        new HelpCommand(null).run();
        mockedUtil.verify(PrintUtil::printCommands, times(1));

        //CommandType is INDEX
        new HelpCommand(CommandType.INDEX).run();
        mockedUtil.verify(PrintUtil::indexHelp, times(1));

        //CommandType is QUERY
        new HelpCommand(CommandType.QUERY).run();
        mockedUtil.verify(PrintUtil::queryHelp, times(1));

        //CommandType is QUIT
        new HelpCommand(CommandType.QUIT).run();
        mockedUtil.verify(PrintUtil::quitHelp, times(1));

        //CommandType is HELP
        new HelpCommand(CommandType.HELP).run();
        mockedUtil.verify(PrintUtil::printCommands, times(2));

        mockedUtil.closeOnDemand();
    }
}