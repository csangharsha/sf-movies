import React, {Component} from 'react';

import './styles.css';

class SearchBox extends Component {

    render() {
        let { searchKeyword } = this.props;

        return (
            <div className={'searchbox'}>
               <input
                   className={'searchbox__input'}
                   autoFocus={true}
                   placeholder={'Search Movie'}
                   value={searchKeyword}
                   onChange={(event) => this.props.onValueChanged(event)}
               />
            </div>
        );
    }

}

export default SearchBox;