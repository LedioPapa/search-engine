package com.se.searchengine.repository;

import com.se.searchengine.model.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<Document, Long> {
}
