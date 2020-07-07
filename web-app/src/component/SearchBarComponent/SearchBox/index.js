import React, {Component} from 'react';
import debounce from 'lodash.debounce';

import './styles.css';

class SearchBox extends Component {

    debounceEvent(...args) {
        this.debouncedEvent = debounce(...args);
        return e => {
            e.persist();
            return this.debouncedEvent(e);
        }
    }

    handleChange = (event) => this.props.onValueChanged(event);

    render() {
        let {searchKeyword} = this.props;

        return (
            <div className={'searchbox'}>
                <input
                    className={'searchbox__input'}
                    autoFocus={true}
                    placeholder={'Search Movie'}
                    value={searchKeyword}
                    onChange={this.debounceEvent(this.handleChange, 500)}
                />
            </div>
        );
    }

}

export default SearchBox;