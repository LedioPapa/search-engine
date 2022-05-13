package com.se.command;

import com.se.engine.ElasticSearchEngine;
import com.se.model.Document;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adds one document to the index
 */
@AllArgsConstructor
@EqualsAndHashCode
public class IndexCommand implements Command{

    private final List<String> commandParts;

    /**
     * Creates the document from the resolved input
     * and call the {@link ElasticSearchEngine} to add the document to the index
     */
    @Override
    public void run() {
        Document document = new Document(Long.parseLong(commandParts.get(1)),
                commandParts.stream()
                        .skip(2)
                        .collect(Collectors.toList()));
        ElasticSearchEngine.getInstance().index(document);
    }
}
