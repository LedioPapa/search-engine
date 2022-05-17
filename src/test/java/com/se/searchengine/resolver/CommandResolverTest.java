package com.se.searchengine.resolver;

import com.se.searchengine.common.CommandType;
import com.se.searchengine.common.PrintUtil;
import com.se.searchengine.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CommandResolverTest {

    @Mock
    SearchService searchService;
    CommandResolver resolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resolver = new CommandResolver();
        resolver.setSearchService(searchService);
    }

    @Test
    public void resolveUnknownCommand() {
        MockedStatic<PrintUtil> mockedUtil = mockStatic(PrintUtil.class);
        resolver.resolve("test");
        mockedUtil.verify(() -> PrintUtil.unknownCommand("test"), times(1));
        mockedUtil.closeOnDemand();
    }

    @Test
    public void resolveHelpCommand() {
        //help without arguments
        resolver.resolve("help");
        verify(searchService, times(1)).help(null);

        //help with unknown argument
        resolver.resolve("help dummy");
        verify(searchService, times(2)).help(null);

        //help with index argument
        resolver.resolve("help index");
        verify(searchService, times(1)).help(CommandType.INDEX);

        //help with query argument
        resolver.resolve("help query");
        verify(searchService, times(1)).help(CommandType.QUERY);

        //help with quit argument
        resolver.resolve("help quit");
        verify(searchService, times(1)).help(CommandType.QUIT);
    }

    @Test
    public void resolveIndexCommand() {
        doNothing().when(searchService).index(anyList());
        //without arguments
        resolver.resolve("index");
        verify(searchService, times(0)).index(any());

        //doc_id not number
        resolver.resolve("index l test");
        verify(searchService, times(0)).index(any());

        //token contains unsupported characters
        resolver.resolve("index 1 test$");
        verify(searchService, times(0)).index(any());

        //correct values
        resolver.resolve("index 1 test");
        verify(searchService, times(1)).index(any());
    }

    @Test
    public void resolveQueryCommand() {
        //without arguments
        resolver.resolve("query");
        verify(searchService, times(0)).query(any());

        //unsupported characters
        resolver.resolve("query milk#");
        verify(searchService, times(0)).query(any());

        //correct values
        resolver.resolve("query milk&honey");
        verify(searchService, times(1)).query(any());
    }

}