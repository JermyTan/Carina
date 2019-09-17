import React, { Component } from "react";
import { Map, GoogleApiWrapper, Marker, Circle } from "google-maps-react";

import CurrLocationSvg from "../svgs/curr-location.svg";

interface ICarparkMapProps {
  location: {
    lat: number;
    lng: number;
  };
  zoom: number;
  radius: number;
  // Indexer, for any other props passed by higher order component
  [x: string]: any;
}

const mapStyles = {
  width: "100%",
  height: "100%",
};

class CarparkMap extends Component<ICarparkMapProps, any> {
  render() {
    return (
      <Map
        google={this.props.google}
        zoom={16}
        style={mapStyles}
        initialCenter={this.props.location}
        center={this.props.location}
        streetViewControl={false}
        mapTypeControl={false}
        fullscreenControl={false}
        gestureHandling="auto"
      >
        <Marker position={this.props.location} icon={CurrLocationSvg} />
        {this.props.markers.map((marker: any, index: number) => (
          <Marker key={index} position={marker} />
        ))}
        <Circle
          radius={this.props.radius}
          center={this.props.location}
          strokeColor="transparent"
          strokeOpacity={0}
          strokeWeight={5}
          fillColor="#50C878"
          fillOpacity={0.2}
        />
      </Map>
    );
  }
}

export default GoogleApiWrapper({
  // NOTE: you must have a valid google map API key in your environmental variables
  apiKey: process.env.REACT_APP_GOOGLE_MAP_API_KEY!,
  //@ts-ignore
})(CarparkMap);
