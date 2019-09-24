import React, { Component, useEffect } from "react";
import {
  Map,
  GoogleApiWrapper,
  Marker,
  Circle,
  InfoWindow,
} from "google-maps-react";
// @ts-ignore
import MarkerClusterer from "@google/markerclustererplus";

import CurrLocationSvg from "../svgs/curr-location.svg";
import { Location } from "../pages/Main";

import "../styles/CarparkMap.scss";

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

interface ICarparkMapState {
  showingInfoWindow: boolean;
  activeMarker: Location | {};
  selectedPlace: any;
  map: any;
}

const mapStyles = {
  width: "100%",
  height: "100%",
};

class CarparkMap extends Component<ICarparkMapProps, ICarparkMapState> {
  constructor(props: ICarparkMapProps) {
    super(props);

    this.state = {
      showingInfoWindow: false,
      activeMarker: {},
      selectedPlace: {},
      map: null,
    };
  }

  updateMap = (mapProps: any, map: any) => {
    this.setState({ map });
  };

  onCenterButtonClicked = () => {
    this.state.map.setCenter(this.props.location);
  };

  onMarkerClick = (props: any) => {
    this.setState({
      activeMarker: props.entry as Location,
      showingInfoWindow: true,
    });
  };

  onCloseInfoWindow = () => {
    if (this.state.showingInfoWindow) {
      this.setState({
        showingInfoWindow: false,
        activeMarker: {},
      });
    }
  };

  render() {
    return (
      <>
        <button
          onClick={this.onCenterButtonClicked}
          title="Center"
          aria-label="Center"
          type="button"
          className="center-map-btn"
        >
          <i className="fa fa-location-arrow"></i>
        </button>
        <Map
          onReady={this.updateMap}
          google={this.props.google}
          onClick={this.onCloseInfoWindow}
          zoom={16}
          style={mapStyles}
          initialCenter={this.props.location}
          center={this.props.location}
          streetViewControl={false}
          mapTypeControl={false}
          fullscreenControl={false}
          gestureHandling="auto"
        >
          <MarkerCluster
            click={this.onMarkerClick}
            markers={this.props.markers}
          />
          <Marker position={this.props.location} icon={CurrLocationSvg} />
          <InfoWindow
            // @ts-ignore
            marker={this.state.activeMarker}
            visible={this.state.showingInfoWindow}
            onClose={this.onCloseInfoWindow}
          >
            <div>{(this.state.activeMarker as Location).id}</div>
          </InfoWindow>
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
      </>
    );
  }
}

const evtNames = [
  "click",
  "dblclick",
  "dragend",
  "mousedown",
  "mouseout",
  "mouseover",
  "mouseup",
  "recenter",
];

const MarkerCluster: React.FunctionComponent<any> = props => {
  const { map, google, markers } = props;

  const handleEvent = ({ event, marker, entry }: any) => {
    if (props[event]) {
      props[event]({
        props: props,
        marker: marker,
        event: event,
        entry: entry,
      });
    }
  };

  // This hook works like ComponentWillMount
  // The  hook isn't really needed, this whole thing worked without it,
  // I added the hook so that I could implement a cleanup function
  useEffect(() => {
    if (map && markers) {
      const mapMarkers = markers.map((marker: Location) => {
        const entry = new google.maps.Marker({
          position: {
            lat: parseFloat(marker.lat),
            lng: parseFloat(marker.lng),
          },
          map: map,
          id: marker.id,
        });

        evtNames.forEach(e => {
          entry.addListener(e, () =>
            handleEvent({
              event: e,
              marker: marker,
              entry: entry,
            })
          );
        });

        return entry;
      });

      const clusterer = new MarkerClusterer(map, mapMarkers);

      // Cleanup function. Note, this is only returned if we create the markers
      return () => {
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
