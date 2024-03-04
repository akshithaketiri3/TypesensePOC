package com.example.typesense.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;


@Data
public class SearchDTO {

    private int page = 0;


    private int size = 50;


    private List<RangeFilter> rangeFilters;


    private List<EqualFilter> equalFilters;


}
