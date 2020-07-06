import React, {Component} from 'react';
import './App.css';
import SearchBarComponent from "./component/SearchBarComponent";
import MapComponent from "./component/MapComponent";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            movies: [],
            selectedMovie: undefined,
            searchKeyword: ""
        };
    }

    componentDidMount() {
        this.getMovies(this.state.searchKeyword);
    }

    getMovies = async (value) => {

        try {
            await fetch(`/api/movies/search?keyword=${value}`)
                .then(response => response.json())
                .then(movies => this.setState({movies}));
        } catch (error) {
            // TODO: handle error
        } finally {

        }
    }

    onValueChanged = (event) => {
        this.setState({ searchKeyword: event.target.value });
        this.getMovies(event.target.value);
    }

    onMovieItemClicked = (value) => {
        this.setState({ selectedMovie: value });
    }

    render() {
        return (
            <div className="App">
                <div className={'App'}>
                    <div className={'search-bar'}>
                        <SearchBarComponent
                            searchKeyword={this.state.searchKeyword}
                            movies={this.state.movies}
                            onValueChanged={this.onValueChanged}
                            onMovieItemClicked={this.onMovieItemClicked}/>
                    </div>
                    <div className={'map'}>
                        <MapComponent zoom={5} movie={this.state.selectedMovie} />
                    </div>
                </div>
            </div>
        );
    }

}

export default App;
