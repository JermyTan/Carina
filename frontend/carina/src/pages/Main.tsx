import React from "react";
import CarparkMap from "../components/CarparkMap";
// @ts-ignore
import GooglePlacesAutocomplete from "react-google-places-autocomplete";
// @ts-ignore
import { geocodeByPlaceId, getLatLng } from "react-google-places-autocomplete";

import "styles/Main.scss";

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

  // TODO: Add state to keep track of location entered
  render() {
    const radiusInt =
      this.state.radius === "" ? 0 : parseInt(this.state.radius);

    return (
      <div className="row">
        <div className="col-6">
          <form>
            <div className="form-group">
              <GooglePlacesAutocomplete
                inputClassName="form-control"
                autocompletionRequest={{
                  componentRestrictions: { country: "sg" },
                }}
                initialValue={this.state.address}
                onSelect={this.handleSelectLocation}
              />
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
            <input type="submit" value="Submit" />
          </form>
        </div>
        <div className="map-wrapper col-6">
          <CarparkMap location={this.state.location} radius={radiusInt} />
        </div>
      </div>
    );
  }
}

export default MainPage;
