import React, {Component} from "react";
import MovieItem from "./MovieItem";
import './styles.css';

class MovieList extends Component {

    render() {
        const {movies} = this.props;

        const movieList = movies.map(function (movie) {
            return (
                <MovieItem onMovieItemClicked={this.props.onMovieItemClicked} key={movie.id} movie={movie} />
            )
        }, this);

        return (
            <div className='movie-list'>
                <div className='list-group'>
                    {movieList}
                </div>
            </div>
        );
    }

}

export default MovieList;