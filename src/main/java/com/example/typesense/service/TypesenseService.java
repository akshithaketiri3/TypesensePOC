package com.example.typesense.service;

import com.example.typesense.client.TypesenseClient;
import com.example.typesense.model.EqualFilter;
import com.example.typesense.model.RangeFilter;
import com.example.typesense.model.SearchDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.typesense.model.*;

import java.nio.file.Path;
import java.util.ArrayList;


@Service
@Data
public class TypesenseService {
    @Autowired
    TypesenseClient typesenseClient;

    public String importToCollection(Path path, String name) throws Exception {
        ImportDocumentsParameters importDocumentsParameters = new ImportDocumentsParameters();
        importDocumentsParameters.action("create");
        return typesenseClient.importDataToCollection(name, path, importDocumentsParameters);
    }

    public String createCollection(String name, ArrayList<Field>fields, String sortField, Boolean enableNested) throws Exception {
        return typesenseClient.createCollection(name, fields, sortField, enableNested);
    }

    public  SearchResult createSearchParameter(String name, SearchDTO searchDTO) throws Exception {

         String filter = "";

         for(EqualFilter ef :  searchDTO.getEqualFilters()){
             String s = String.join(", ", ef.getEqualsIn());
             filter += ef.getKey() + ":=" +  "["  + s  + "]" + " && ";
         }

         for(RangeFilter rf : searchDTO.getRangeFilters()){
             filter += rf.getKey() + ":[" + rf.getMin() + ".." + rf.getMax() + "]" + " && ";
         }


        if (filter.endsWith(" && ")) {
            filter = filter.substring(0, filter.length() - 4);
        }


        SearchParameters searchParameters = new SearchParameters()
                .q("*")
                .queryBy("invoiceId")
                .page(searchDTO.getPage())
                .perPage(searchDTO.getSize())
                .filterBy(filter)
                .infix("always");


        return typesenseClient.getClient().collections(name).documents().search(searchParameters);

    }

    public CollectionResponse retrieve(String name) throws Exception {
        return typesenseClient.retrieve(name);
    }

    public CollectionResponse delete(String name) throws Exception{
        return typesenseClient.delete(name);
    }
}
