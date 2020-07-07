import React, { Component } from 'react';
import { Map, TileLayer, Marker, Popup } from 'react-leaflet'

import './styles.css';

class MapComponent extends Component {

    render() {

        let center = this.props.center ? this.props.center : [37.770015, -122.446937];
        const zoom = this.props.zoom ? this.props.zoom : 14;
        const { movie } = this.props;
        center = movie ? [movie.lat, movie.lon] : center;
        const marker = movie ? (
            <Marker position={[movie.lat, movie.lon]} key={movie.id} >
                <Popup>
                    <span><strong>Title: </strong>{movie.title}</span>
                    <br/>
                    <span><strong>Location: </strong>{movie.locations}</span><br/>
                </Popup>
            </Marker>
        ) : null;

        return (
            <div className={'map'}>
                <Map
                    center = {center}
                    zoom = {zoom}
                    style={{ width: '100%', height: '100vh' }}>
                    <TileLayer
                        attribution='&copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {marker}
                </Map>
            </div>
        );

    }

}

export default MapComponent;