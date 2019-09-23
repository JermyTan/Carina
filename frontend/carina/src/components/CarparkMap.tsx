import React, { Component, useEffect } from "react";
import { Map, GoogleApiWrapper, Marker, Circle } from "google-maps-react";
// @ts-ignore
import MarkerClusterer from "@google/markerclustererplus";

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
        <MarkerCluster markers={this.props.markers} />
        <Marker position={this.props.location} icon={CurrLocationSvg} />
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

const MarkerCluster: React.FunctionComponent<any> = props => {
  const { map, google, markers } = props;

  // This hook works like ComponentWillMount
  // The  hook isn't really needed, this whole thing worked without it,
  // I added the hook so that I could implement a cleanup function
  useEffect(() => {
    if (map && markers) {
      const mapMarkers = markers.map((position: any) => {
        const entry = new google.maps.Marker({
          position: {
            lat: parseFloat(position.lat),
            lng: parseFloat(position.lng),
          },
          map: map,
        });

        return entry;
      });

      const clusterer = new MarkerClusterer(map, mapMarkers);

      // Cleanup function. Note, this is only returned if we create the markers
      return () => {
        console.log("Cleaning up markers");
        clusterer.clearMarkers();
      };
    }
  }, [map, google, markers]);

  // Do we need to render anything??
  return null;
};

export default GoogleApiWrapper({
  // NOTE: you must have a valid google map API key in your environmental variables
  apiKey: process.env.REACT_APP_GOOGLE_MAP_API_KEY!,
  //@ts-ignore
})(CarparkMap);
