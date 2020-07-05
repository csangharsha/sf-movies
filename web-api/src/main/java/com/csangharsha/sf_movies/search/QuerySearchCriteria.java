package com.csangharsha.sf_movies.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuerySearchCriteria {

    private String key;

    private String operation;

    private Object value;

}
