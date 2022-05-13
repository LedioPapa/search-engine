package com.se.command;

import com.se.engine.ElasticSearchEngine;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Queries the index
 */
@AllArgsConstructor
@EqualsAndHashCode
public class QueryCommand implements Command{
    private final String expression;

    /**
     * Calls {@link ElasticSearchEngine} passing the resolved
     * expression
     */
    @Override
    public void run() {

        ElasticSearchEngine.getInstance().query(expression);

    }
}
