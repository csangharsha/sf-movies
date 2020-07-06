import React, { Component } from 'react';
import SearchBox from "./SearchBox";
import MovieList from "./MovieList";
import './styles.css';

class SearchBarComponent extends Component {

    render() {
        const { searchKeyword, movies } = this.props;
        return (
            <div className='searchbar-container'>
                <div>
                    <SearchBox
                        search={searchKeyword}
                        onValueChanged={this.props.onValueChanged} />
                </div>
                <div>
                    <MovieList
                        movies={movies}
                        onMovieItemClicked={this.props.onMovieItemClicked} />
                </div>
            </div>
        )
    }

}

export default SearchBarComponent;