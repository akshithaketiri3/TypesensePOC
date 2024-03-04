package com.example.typesense.client;

import com.example.typesense.config.TypesenseConfigProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.typesense.api.*;
import org.typesense.model.*;
import org.typesense.resources.*;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Data
@Component
public class TypesenseClient {

    private final TypesenseConfigProperties typesenseConfigProperties;
    private List<Node>nodes;
    private Configuration configuration;
    private Client client;



    public TypesenseClient(TypesenseConfigProperties typesenseConfigProperties) throws Exception {
        this.typesenseConfigProperties = typesenseConfigProperties;
        this.nodes = new ArrayList<>();
        this.nodes.add(new Node(this.typesenseConfigProperties.getProtocol(), this.typesenseConfigProperties.getHost(), this.typesenseConfigProperties.getPort()));
        this.configuration = new Configuration(nodes, Duration.ofSeconds(20), this.typesenseConfigProperties.getApikey());
        this.client= new Client(configuration);
    }

    public String createCollection(String name, ArrayList<Field>fields, String field, Boolean enableNested){
        String response;
        try {
            CollectionSchema collectionSchema = new CollectionSchema();
            collectionSchema.name(name);
            collectionSchema.setFields(fields);
            collectionSchema.setDefaultSortingField(field);
            collectionSchema.setEnableNestedFields(enableNested);
            client.collections().create(collectionSchema);
            response = "SUCCESS";
        }catch (Exception e){
            log.error(e.getMessage());
            response = "FAILURE";
        }
        return response;

    }

    public String importDataToCollection(String collectionName, Path path, ImportDocumentsParameters searchParameters) throws Exception {

        String Data = Files.readString(path);
        String response;
        try {

            ImportDocumentsParameters queryParameters = new ImportDocumentsParameters();
            queryParameters.batchSize(100);

          response =   client.collections(collectionName).documents().import_(Data, queryParameters);
        }catch (Exception e){
            log.error(e.getMessage());
            response = "FAILURE";
        }
        return response;
    }

    public SearchResult createSearchParameter(String name, String text, ArrayList<String>fields) throws Exception {
        String commaSeparatedString = String.join(",", fields);
        SearchParameters searchParameters = new SearchParameters()
                .q(text)
                .queryBy(commaSeparatedString);

       return  client.collections(name).documents().search(searchParameters);

    }
    public CollectionResponse retrieve(String name) throws Exception {
        return client.collections(name).retrieve();
    }

    public CollectionResponse delete(String name) throws Exception {
        return client.collections(name).delete();
    }









}
