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
import { Point, Carpark } from "../utils/Types";

import "../styles/CarparkMap.scss";

interface ICarparkMapProps {
  location: Point;
  zoom: number;
  radius: number;
  carparks: Carpark[];
  // Indexer, for any other props passed by higher order component
  [x: string]: any;
}

interface ICarparkMapState {
  showingInfoWindow: boolean;
  activeMarker: {};
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
      activeMarker: props.entry,
      selectedPlace: props.carpark,
      showingInfoWindow: true,
    });

    this.props.handleMarkerClick(props.carpark.carparkId);
  };

  onCloseInfoWindow = () => {
    if (this.state.showingInfoWindow) {
      this.setState({
        showingInfoWindow: false,
        activeMarker: {},
      });
    }

    this.props.handleInfoWindowClose();
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
          center={this.props.currentLatLng}
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
            <InfoDetails selectedCarpark={this.state.selectedPlace} />
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

const InfoDetails = ({ selectedCarpark }: { selectedCarpark: Carpark }) => {
  console.log(selectedCarpark);

  const classifyLotCount = (numLots: number) => {
    if (numLots >= 100) return "high";
    if (numLots >= 30) return "med";
    return "low";
  };

  return (
    <div className="info-details-container">
      <div className="info-header">{selectedCarpark.address}</div>
      <div className="info-subtitle">{selectedCarpark.subAddress}</div>
      {selectedCarpark.availableLots && (
        <div
          className={`lot-count ${classifyLotCount(
            parseInt(selectedCarpark.availableLots)
          )}`}
        >
          {selectedCarpark.availableLots} lots left
        </div>
      )}
    </div>
  );
};

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

  const handleEvent = ({ event, carpark, entry }: any) => {
    if (props[event]) {
      props[event]({
        carpark,
        event,
        entry,
      });
    }
  };

  // This hook works like ComponentWillMount
  // The  hook isn't really needed, this whole thing worked without it,
  // I added the hook so that I could implement a cleanup function
  useEffect(() => {
    if (map && markers) {
      const mapMarkers = markers.map((carpark: Carpark) => {
        const entry = new google.maps.Marker({
          position: {
            lat: carpark.latitude,
            lng: carpark.longitude,
          },
          map,
        });

        evtNames.forEach(event => {
          entry.addListener(event, () =>
            handleEvent({
              event,
              carpark,
              entry,
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
  }, [map, google, markers as Carpark[]]);

  // Do we need to render anything??
  return null;
};

export default GoogleApiWrapper({
  // NOTE: you must have a valid google map API key in your environmental variables
  apiKey: process.env.REACT_APP_GOOGLE_MAP_API_KEY!,
  //@ts-ignore
})(CarparkMap);
