import React, {Component} from 'react';
import './App.css';
import SearchBarComponent from "./component/SearchBarComponent";

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
        this.setState({ electedMovie: value });
    }

    render() {
        return (
            <div className="App">
                <div className={'App'}>
                    <div className={'map'}>

                    </div>
                    <div>
                        <SearchBarComponent
                            searchKeyword={this.state.searchKeyword}
                            movies={this.state.movies}
                            onValueChanged={this.onValueChanged}
                            onMovieItemClicked={this.onMovieItemClicked}/>
                    </div>
                </div>
            </div>
        );
    }

}

export default App;
