package com.se.engine;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.se.common.CommandType;
import com.se.common.PrintUtil;
import com.se.model.Document;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contains logic to add documents to index
 * in an ElasticSearch node
 */
public class ElasticSearchEngine {
    private static ElasticSearchEngine instance;
    private final ElasticsearchClient client;

    private ElasticSearchEngine() {
        RestClient restClient = RestClient.builder(new HttpHost(System.getenv("ELASTIC_HOSTNAME"),
                Integer.parseInt(System.getenv("ELASTIC_PORT")))).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
    }

    public static ElasticSearchEngine getInstance() {
        if (instance == null)
            instance = new ElasticSearchEngine();
        return instance;
    }

    /**
     * Adds document to the index and prints the response
     * and the duration of the process
     * If something goes wrong, it prints the error in stdout
     * @param document
     */
    public void index(Document document) {
        try {
            long start = System.currentTimeMillis();
            client.index(i -> i
                    .index("documents")
                    .id(document.getId().toString())
                    .document(document));
            long duration = System.currentTimeMillis() - start;
            PrintUtil.indexOk(CommandType.INDEX, duration);
        } catch (IOException | ElasticsearchException e) {
            PrintUtil.commandError(CommandType.INDEX.getCommand(), e.getMessage());
        }
    }

    /**
     * Queries the documents in the index using an expression
     * Prints the {@link Document} ids to stdout
     * If something goes wrong, it prints the error in stdout
     * @param expression
     */
    public void query(String expression) {
        try {
            long start = System.currentTimeMillis();

            SearchRequest  searchRequest = new SearchRequest.Builder().index("documents")
                    .query(QueryBuilders.queryString().query(expression).build()._toQuery())
                    .build();

            SearchResponse<Document> response = client.search(searchRequest, Document.class);

            long duration = System.currentTimeMillis() - start;
            List<Long> hits = response.hits().hits().stream().map(Hit::source).filter(Objects::nonNull).map(Document::getId).collect(Collectors.toList());
            PrintUtil.queryResult(hits, duration);
        } catch (IOException | ElasticsearchException e) {
            PrintUtil.commandError(CommandType.QUERY.getCommand(), e.getMessage());
        }
    }

}
