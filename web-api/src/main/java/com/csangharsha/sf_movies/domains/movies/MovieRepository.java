package com.csangharsha.sf_movies.domains.movies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

    List<Movie> findAllByTitleContaining(String title);

}
