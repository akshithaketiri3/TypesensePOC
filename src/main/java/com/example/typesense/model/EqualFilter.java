package com.example.typesense.model;

import lombok.Data;
import java.util.List;

@Data

public class EqualFilter {

    private String key;
    private List<String> equalsIn;
}
