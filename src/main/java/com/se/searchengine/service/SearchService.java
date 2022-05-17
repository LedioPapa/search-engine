package com.se.searchengine.service;

import com.se.searchengine.common.CommandType;
import com.se.searchengine.common.PrintUtil;
import com.se.searchengine.model.Document;
import com.se.searchengine.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final DocumentRepository repository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * Defines the command type and prints the output accordingly
     * Otherwise prints the command list
     */
    public void help(CommandType commandType) {
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

    /**
     * Creates the document from the resolved input
     * and call the {@link SearchService} to add the document to the index
     */
    public void index(List<String> commandParts) {
        try {
            long start = System.currentTimeMillis();
            Document document = new Document(Long.parseLong(commandParts.get(1)),
                    commandParts.stream()
                            .skip(2)
                            .collect(Collectors.toList()));
            repository.save(document);
            PrintUtil.indexOk(System.currentTimeMillis() - start);
        } catch (Exception e) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), e.getMessage());
            log.error("Error adding document to the index", e);
        }
    }

    /**
     * Calls {@link DocumentRepository} passing the resolved
     * expression
     */
    public void query(String expression) {
        try {
            long start = System.currentTimeMillis();
            Query query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(expression))
                    .build();
            SearchHits<Document> result = elasticsearchRestTemplate.search(query, Document.class);
            List<Long> ids = result.getSearchHits()
                    .stream()
                    .map(SearchHit::getContent)
                    .map(Document::getId)
                    .collect(Collectors.toList());
            PrintUtil.queryResult(ids, System.currentTimeMillis() - start);
        } catch (Exception e) {
            PrintUtil.commandError(CommandType.QUERY.getCommand(), e.getMessage());
            log.error("Error adding document to the index", e);
        }
    }
}
