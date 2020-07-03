package com.csangharsha.sf_movies.movies;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    public Movie preSave(Movie movie) {
        return movie;
    }

    @Override
    public Movie save(Movie movie) {
        movie = preSave(movie);
        return repository.save(movie);
    }

    @Override
    public List<Movie> saveAll(List<Movie> movies) {
        return repository.saveAll(movies);
    }

    @Override
    public Movie update(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Movie> findOne(String id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Movie movie) {
        repository.delete(movie);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
