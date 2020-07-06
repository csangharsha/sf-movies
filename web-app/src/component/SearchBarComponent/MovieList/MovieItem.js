import React, {Component} from 'react';

class MovieItem extends Component {

    render() {
        const {movie} = this.props;
        return (
            <div>
                <div onClick={() => this.props.onMovieItemClicked(movie)} className='list-group-item' style={{marginBottom: '10px', cursor: 'pointer'}}>
                    <h4>{movie.title}</h4>
                    <div><strong>{'Location: '}</strong>{movie.locations}</div>
                    <div><strong>{'Release Year : '}</strong>{movie.release_year}</div>
                    <div><strong>{'Actors : '}</strong>{movie.actor_1 + ', ' + movie.actor_2 + ', ' + movie.actor_3}</div>
                    <div><strong>{'Director : '}</strong>{movie.director}</div>
                </div>
            </div>
        );
    }

}

export default MovieItem;