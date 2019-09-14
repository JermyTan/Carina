import React from "react";
// @ts-ignore
import GooglePlacesAutocomplete from "react-google-places-autocomplete";
// @ts-ignore
import { geocodeByPlaceId, getLatLng } from "react-google-places-autocomplete";
// @ts-ignore
import Geocode from "react-geocode";

import CarparkMap from "../components/CarparkMap";
import CarparkInfo from "../components/CarparkInfo";
import LocationSvg from "../components/svgrs/LocationSvg";

import "styles/Main.scss";

Geocode.setApiKey(process.env.REACT_APP_GOOGLE_MAP_API_KEY);

interface IMainPageState {
  location: {
    lat: number;
    lng: number;
  };
  zoom: number;
  radius: string;

  address: string;
}

class MainPage extends React.Component<any, IMainPageState> {
  constructor(props: any) {
    super(props);

    this.state = {
      location: {
        lat: 1.2935861,
        lng: 103.7844513,
      },
      zoom: 16,
      radius: "300",
      address: "Kent Ridge MRT Station",
    };

    this.handleRadiusChange = this.handleRadiusChange.bind(this);
    this.handleBlur = this.handleBlur.bind(this);
    this.handleSelectLocation = this.handleSelectLocation.bind(this);
    this.requestLocation = this.requestLocation.bind(this);
  }

  handleRadiusChange(event: React.ChangeEvent<HTMLInputElement>) {
    this.setState({ radius: event.target.value });
  }

  handleBlur() {
    this.setState({
      radius: this.state.radius === "" ? "0" : this.state.radius,
    });
  }

  handleSelectLocation(result: any) {
    const { place_id, description } = result;
    geocodeByPlaceId(place_id)
      .then((results: any) => getLatLng(results[0]))
      .then((location: any) => {
        this.setState({ address: description, location });
      });
  }

  requestLocation() {
    if (navigator.geolocation) {
      const updatePosition = (position: Position) => {
        const { latitude, longitude } = position.coords;
        Geocode.fromLatLng(latitude, longitude).then((response: any) => {
          const address = response.results[0].formatted_address;
          this.setState({
            location: {
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            },
            address,
          });
        });
      };

      const showError = (error: any) => {
        switch (error.code) {
          case error.PERMISSION_DENIED:
            alert(
              "To get current location, please allow location access for this application."
            );
            break;
          case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
          case error.TIMEOUT:
            alert("The request to get current location timed out.");
            break;
          case error.UNKNOWN_ERROR:
            alert("An unknown error occurred.");
            break;
        }
      };

      navigator.geolocation.getCurrentPosition(updatePosition, showError);
    } else {
      alert("Geolocation is not supported for this Browser/OS.");
    }
  }

  render() {
    const radiusInt =
      this.state.radius === "" ? 0 : parseInt(this.state.radius);

    return (
      <div className="row no-gutters">
        <div className="col-lg-7 left-col">
          {/* Start of form */}
          <form>
            <div className="form-group">
              <div className="input-group mb-2">
                <GooglePlacesAutocomplete
                  inputClassName="form-control"
                  autocompletionRequest={{
                    componentRestrictions: { country: "sg" },
                  }}
                  initialValue={this.state.address}
                  onSelect={this.handleSelectLocation}
                />
                <div className="input-group-append">
                  <div
                    className="input-group-text"
                    onClick={this.requestLocation}
                  >
                    <LocationSvg />
                  </div>
                </div>
              </div>
              <label htmlFor="radiusInput">Search radius</label>
              <div className="input-group mb-2">
                <input
                  className="form-control"
                  id="radiusInput"
                  type="number"
                  value={this.state.radius}
                  onChange={this.handleRadiusChange}
                  onBlur={this.handleBlur}
                />
                <div className="input-group-append">
                  <div className="input-group-text">metres</div>
                </div>
              </div>
            </div>
          </form>
          {/* End of form */}

          <section className="carparks-header">
            3 carparks within radius
          </section>
          <div className="carparks">
            {new Array(10).fill(0).map(i => (
              <CarparkInfo location={{ lat: 1.2935861, lng: 103.7844513 }} />
            ))}
          </div>
        </div>
        <div className="map-wrapper col-lg-5">
          <CarparkMap location={this.state.location} radius={radiusInt} />
        </div>
      </div>
    );
  }
}

export default MainPage;
