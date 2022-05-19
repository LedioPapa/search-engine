package com.se.searchengine.service;

import com.se.searchengine.common.CommandType;
import com.se.searchengine.common.PrintUtil;
import com.se.searchengine.model.Document;
import com.se.searchengine.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchServiceTest {

    SearchService searchService;

    @Mock
    DocumentRepository repository;

    @Mock
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    MockedStatic<PrintUtil> mockedUtil;

    @BeforeEach
    void setUp() {
        openMocks(this);
        searchService = new SearchService(repository, elasticsearchRestTemplate);
        mockedUtil = mockStatic(PrintUtil.class);
    }

    @Test
    public void help() {
        //CommandType is null
        searchService.help(null);
        mockedUtil.verify(PrintUtil::printCommands, times(1));

        //CommandType is INDEX
        searchService.help(CommandType.INDEX);
        mockedUtil.verify(PrintUtil::indexHelp, times(1));

        //CommandType is QUERY
        searchService.help(CommandType.QUERY);
        mockedUtil.verify(PrintUtil::queryHelp, times(1));

        //CommandType is QUIT
        searchService.help(CommandType.QUIT);
        mockedUtil.verify(PrintUtil::quitHelp, times(1));

        //CommandType is HELP
        searchService.help(CommandType.HELP);
        mockedUtil.verify(PrintUtil::printCommands, times(2));

        mockedUtil.closeOnDemand();
    }

    @Test
    public void index() {
        when(repository.save(any(Document.class))).thenReturn(new Document());
        searchService.index(Arrays.asList("index","1","test"));
        mockedUtil.verify(() -> PrintUtil.indexOk(anyLong()));

        //exception
        when(repository.save(any())).thenThrow(new IllegalArgumentException());
        searchService.index(Arrays.asList("index","1","test"));
        mockedUtil.verify(() -> PrintUtil.commandError(any(), any()));

        mockedUtil.closeOnDemand();
    }

    @Test
    public void query() {
        when(elasticsearchRestTemplate.search(any(Query.class), any()))
                .thenReturn(new SearchHitsImpl<>(0, TotalHitsRelation.EQUAL_TO, 0, null, Collections.emptyList(), null,null));
        searchService.query("test");
        mockedUtil.verify(() -> PrintUtil.queryResult(anyList(), anyLong()));

        //exception
        when(elasticsearchRestTemplate.search(any(Query.class), any())).thenThrow(new NullPointerException());
        searchService.query("test");
        mockedUtil.verify(() -> PrintUtil.commandError(any(), any()));

        mockedUtil.closeOnDemand();
    }
}