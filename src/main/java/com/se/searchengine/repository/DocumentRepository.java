package com.se.searchengine.repository;

import com.se.searchengine.model.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<Document, Long> {

    default List<Document> getDocumentsByTokensContaining(String expression){
        return null;
    }
}
