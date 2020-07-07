package com.csangharsha.sf_movies.domains.movies;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @GenericGenerator(
            name = "movie_seq",
            strategy = "com.csangharsha.sf_movies.domains.movies.MovieSequenceIdGenerator",
            parameters = {
                    @Parameter(name = MovieSequenceIdGenerator.ID_SEPARATOR_PARAMETER, value = "_")
            }
    )
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "released_year")
    private String releasedYear;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "locations")
    private String locations;

    @Column(name = "display_location")
    private String displayLocation;

    @Column(name = "production_company")
    private String productionCompany;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "actor_1")
    private String actor1;

    @Column(name = "actor_2")
    private String actor2;

    @Column(name = "actor_3")
    private String actor3;

}
