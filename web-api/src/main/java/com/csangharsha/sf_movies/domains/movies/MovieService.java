package com.csangharsha.sf_movies.domains.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie save(Movie t);

    List<Movie> saveAll(List<Movie> t);

    Movie update(Movie t);

    List<Movie> findAll();

    Page<Movie> findAll(Pageable pageable);

    Optional<Movie> findOne(String id);

    void delete(Movie t);

    void delete(String id);

    boolean existsById(String id);

}
