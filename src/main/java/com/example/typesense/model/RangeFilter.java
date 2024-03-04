package com.example.typesense.model;

import lombok.Data;

@Data
public class RangeFilter {
    private String key;
    private String min;
    private String max;
}
