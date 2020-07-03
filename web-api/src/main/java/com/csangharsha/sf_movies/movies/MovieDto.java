package com.csangharsha.sf_movies.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieDto {

    private String id;

    private String title;

    @JsonProperty("released_year")
    private String releasedYear;

    private Double lat;

    private Double lon;

    private String locations;

    @JsonProperty("production_company")
    private String productionCompany;

    private String director;

    private String writer;

    @JsonProperty("actor_1")
    private String actor1;

    @JsonProperty("actor_2")
    private String actor2;

    @JsonProperty("actor_3")
    private String actor3;

}