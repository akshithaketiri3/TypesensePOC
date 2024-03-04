package com.example.typesense.controller;



import com.example.typesense.client.TypesenseClient;
import com.example.typesense.model.SearchDTO;
import com.example.typesense.service.TypesenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.typesense.model.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TypsenseController {

    @Autowired
    TypesenseService typesenseService;


    @Autowired
    TypesenseClient typesenseClient;

    @PostMapping("/createCollection")
    public String createCollection(@RequestParam("name") String name, @RequestBody ArrayList<Field>fieldRequest, @RequestParam("sorting-field") String field, @RequestParam("enableNested") boolean enableNested) throws Exception {
        return typesenseService.createCollection(name, fieldRequest,field, enableNested);
    }

    @PostMapping("/importToCollection")
    public String importToCollection(@RequestParam("path") Path path, @RequestParam("collection-name") String name) throws Exception{
         return typesenseService.importToCollection(path, name);
    }

    @PostMapping("/createSearchParameter")
    public SearchResult createSearchParameter(@RequestParam("collection-name") String name, @RequestBody SearchDTO searchDT0) throws Exception {

      return  typesenseService.createSearchParameter(name,searchDT0);

    }

    @GetMapping("/retrieveCollection")
    public CollectionResponse retrieveCollection(@RequestParam("collection-name") String name) throws Exception {
       return typesenseClient.retrieve(name);
    }

    @DeleteMapping("/deleteCollection")
    public CollectionResponse deleteCollection(@RequestParam("collection-name") String name) throws Exception {
        return typesenseService.delete(name);
    }

    @GetMapping("/metrics")
    public Map<String, String> retriveMetrics() throws Exception {
       return typesenseClient.getClient().metrics.retrieve();
    }

    @GetMapping("/health")
    public   Map<String, Object> getHealth() throws Exception {
        return typesenseClient.getClient().health.retrieve();
    }

    @PutMapping("/updateSchema")
    public CollectionUpdateSchema updateSchema(@RequestParam("collection-name") String name,  @RequestBody List<Field> fields) throws Exception {
        CollectionUpdateSchema collectionUpdateSchema = new CollectionUpdateSchema();
        collectionUpdateSchema.fields(fields);
        return typesenseClient.getClient().collections(name).update(collectionUpdateSchema);
    }

  


}
