package com.se.command;

import com.se.engine.ElasticSearchEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QueryCommandTest {

    @Mock
    ElasticSearchEngine searchEngine;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void queryCommand() {
        MockedStatic<ElasticSearchEngine> mockedEngine = Mockito.mockStatic(ElasticSearchEngine.class);
        mockedEngine.when(ElasticSearchEngine::getInstance).thenReturn(searchEngine);
        doNothing().when(searchEngine).query(any());

        new QueryCommand("query test").run();
        verify(searchEngine, times(1)).query(any());

        mockedEngine.closeOnDemand();
    }

}