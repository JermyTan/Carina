import React from "react";
import Axios from "axios";
// @ts-ignore
import GooglePlacesAutocomplete from "react-google-places-autocomplete";
// @ts-ignore
import { geocodeByPlaceId, getLatLng } from "react-google-places-autocomplete";
// @ts-ignore
import Geocode from "react-geocode";
import firebase, { auth, provider } from "../firebase";

import CarparkMap from "../components/CarparkMap";
import CarparkInfo from "../components/CarparkInfo";
import LocationSvg from "../components/svgrs/LocationSvg";

import "styles/Main.scss";

Geocode.setApiKey(process.env.REACT_APP_GOOGLE_MAP_API_KEY);

type Carpark = {
  agency: string;
  area: string;
  availableLots: string;
  carparkID: string;
  development: string;
  location: string;
  lotType: string;
  _id: string;
};

type Location = {
  lat: string;
  lng: string;
};

interface IMainPageState {
  location: {
    lat: number;
    lng: number;
  };
  zoom: number;
  radius: string;

  address: string;

  carparks: Carpark[];

  user: any;

  showFavourites: boolean;
  favouritedCarparks: Carpark[];

  // TODO: Move this to backend so we don't have to filter in frontend
  filteredCarparks: Carpark[];
  markers: Location[];
}

class MainPage extends React.Component<any, IMainPageState> {
  constructor(props: any) {
    super(props);

    this.state = {
      location: {
        lat: 1.2935861,
        lng: 103.7844513
      },
      zoom: 16,
      radius: "300",
      address: "Kent Ridge MRT Station",

      user: null,

      showFavourites: false,

      carparks: [],
      favouritedCarparks: [],
      filteredCarparks: [],
      markers: []
    };

    this.handleRadiusChange = this.handleRadiusChange.bind(this);
    this.handleBlur = this.handleBlur.bind(this);
    this.handleSelectLocation = this.handleSelectLocation.bind(this);
    this.requestLocation = this.requestLocation.bind(this);
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
    this.toggleFavourite = this.toggleFavourite.bind(this);
  }

  componentDidMount() {
    auth.onAuthStateChanged(user => {
      if (user) {
        this.setState({ user });
      }
    });

    Axios.get(
      `https://cors-anywhere.herokuapp.com/${process.env.REACT_APP_BACKEND_API}carpark-availability/`
    ).then(response => {
      if (response.status === 200) {
        console.log(response.data);
        const markers = response.data.map((carpark: Carpark) => {
          const location = carpark.location.split(" ");
          return { lat: location[0], lng: location[1] };
        });
        this.setState({
          carparks: response.data,
          markers
        });
      }
    });
  }

  updateCarparksWithinRadius(location: any, radius: string, address: string) {
    const filteredCarparks = this.state.carparks.filter(carpark =>
      this.withinRadius(carpark, location, parseInt(radius))
    );
    this.setState({ location, radius, address, filteredCarparks });
  }

  withinRadius(carpark: Carpark, center: any, radius: number) {
    const point = {
      lat: parseFloat(carpark.location.split(" ")[0]),
      lng: parseFloat(carpark.location.split(" ")[1])
    };
    const R = 6371e3;
    const deg2rad = (n: number) => (n * Math.PI) / 180;

    const dLat = deg2rad(point.lat - center.lat);
    const dLon = deg2rad(point.lng - center.lng);

    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(deg2rad(center.lat)) *
        Math.cos(deg2rad(point.lat)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const d = R * c;
    return d <= radius;
  }

  handleRadiusChange(event: React.ChangeEvent<HTMLInputElement>) {
    this.updateCarparksWithinRadius(
      this.state.location,
      event.target.value,
      this.state.address
    );
  }

  handleBlur() {
    this.setState({
      radius: this.state.radius === "" ? "0" : this.state.radius
    });
  }

  handleSelectLocation(result: any) {
    const { place_id, description } = result;
    geocodeByPlaceId(place_id)
      .then((results: any) => getLatLng(results[0]))
      .then((location: any) => {
        this.updateCarparksWithinRadius(
          location,
          this.state.radius,
          description
        );
      });
  }

  requestLocation() {
    if (navigator.geolocation) {
      const updatePosition = (position: Position) => {
        const { latitude, longitude } = position.coords;
        const location = {
          lat: latitude,
          lng: longitude
        };
        Geocode.fromLatLng(latitude, longitude).then((response: any) => {
          const address = response.results[0].formatted_address;
          this.updateCarparksWithinRadius(location, this.state.radius, address);
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

  logout() {
    auth.signOut().then(() => {
      this.setState({ user: null, showFavourites: false });
      console.log("signed out");
    });
  }

  login() {
    auth.signInWithPopup(provider).then(result => {
      const user = result.user;
      this.setState({ user });
      console.log(user);
    });
  }

  renderLoginButton() {
    if (this.state.user) {
      return (
        <div className="header-login">
          <button className="button" onClick={this.logout}>
            Log Out
          </button>
          <div className="displayName">
            You are currently signed in as {this.state.user.displayName}.
          </div>
        </div>
      );
    } else {
      return (
        <button className="button" onClick={this.login}>
          Log In
        </button>
      );
    }
  }

  toggleFavourite() {
    this.retrieveFavouritedCarparks();
    this.setState({ showFavourites: !this.state.showFavourites });
  }

  retrieveFavouritedCarparks() {
    const { currentUser } = firebase.auth();
    if (currentUser) {
      firebase
        .database()
        .ref(`users/${currentUser.uid}/carparks`)
        .on("value", data => {
          const carparks = data.val();
          const favouritedCarparks = [];
          if (carparks) {
            const keys = Object.keys(carparks);
            for (let i = 0; i < keys.length; i++) {
              let key = keys[i];
              favouritedCarparks.push(carparks[key]);
            }
          }
          this.setState({ favouritedCarparks });
        });
    }
  }

  renderCarparks(carparks: Carpark[]) {
    return carparks.map(carpark => (
      <CarparkInfo
        key={carpark._id}
        carpark={carpark}
        id={carpark._id}
        address={carpark.development}
        subAddress={carpark.area}
        numLots={carpark.availableLots}
        location={{
          lat: parseFloat(carpark.location.split(" ")[0]),
          lng: parseFloat(carpark.location.split(" ")[1])
        }}
        showFavourite={this.state.user != null}
        isFavourited={false}
      />
    ));
  }

  render() {
    return (
      <div className="row no-gutters">
        <div className="col-lg-7 left-col">
          <div className="header">{this.renderLoginButton()}</div>
          {/* Start of form */}
          <form>
            <div className="form-group">
              <div className="input-group mb-2">
                <GooglePlacesAutocomplete
                  inputClassName="form-control"
                  autocompletionRequest={{
                    componentRestrictions: { country: "sg" }
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
            {!this.state.showFavourites && (
              <div className="label">
                {this.state.filteredCarparks.length} carparks within radius
              </div>
            )}
            {this.state.user && (
              <button
                className="toggle-favourite"
                onClick={this.toggleFavourite}
              >
                {this.state.showFavourites ? "Cancel" : "Show favourites"}
              </button>
            )}
          </section>
          <div className="carparks">
            {this.renderCarparks(
              this.state.showFavourites
                ? this.state.favouritedCarparks
                : this.state.filteredCarparks
            )}
          </div>
        </div>
        <div className="map-wrapper col-lg-5">
          <CarparkMap
            location={this.state.location}
            radius={this.state.radius === "" ? 0 : parseInt(this.state.radius)}
            markers={this.state.markers}
          />
        </div>
      </div>
    );
  }
}

export default MainPage;
