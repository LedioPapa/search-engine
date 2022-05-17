package com.se.searchengine.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documents")
public class Document {
    private Long id;
    private List<String> tokens;
}
