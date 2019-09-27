import React, { createRef } from "react";
import Axios from "axios";
import GooglePlacesAutocomplete, {
  geocodeByPlaceId,
  getLatLng,
  // @ts-ignore
} from "react-google-places-autocomplete";
// @ts-ignore
import Geocode from "react-geocode";
import { debounce } from "throttle-debounce";
import firebase, { auth, provider } from "../firebase";
import CarparkList from "../components/CarparkList";
import CarparkMap from "../components/CarparkMap";
import {
  LOCAL_STORAGE_CARPARKS,
  LOCAL_STORAGE_FAVOURITED,
} from "../utils/Constants";
import { Carpark, Point } from "../utils/Types";
import {
  mapEntriesToCarparks,
  updateCarparksDistFromSrc,
} from "../utils/MainUtils";

import "styles/Main.scss";

Geocode.setApiKey(process.env.REACT_APP_GOOGLE_MAP_API_KEY);

interface IMainPageState {
  location: Point;
  selectedLatLng: Point;
  zoom: number;
  radius: string;
  address: string;
  carparks: Carpark[];
  user: any;
  showFavourites: boolean;
  favouritedCarparks: Carpark[];
  favouritedCarparkIds: object;
  nearbyCarparks: Carpark[];

  // refs for use in scrolling
  refs: any;
  selectedId?: string;
}

class MainPage extends React.Component<any, IMainPageState> {
  constructor(props: any) {
    super(props);

    const point: Point = {
      lat: 1.2935861,
      lng: 103.7844513,
    };
    this.state = {
      location: point,
      selectedLatLng: point,
      zoom: 16,
      radius: "300",
      address: "Kent Ridge MRT Station",
      carparks: [],
      user: null,
      showFavourites: false,
      favouritedCarparks: [],
      favouritedCarparkIds: {},
      nearbyCarparks: [],
      refs: {},
    };

    this.handleRadiusChange = this.handleRadiusChange.bind(this);
    this.handleBlur = this.handleBlur.bind(this);
    this.handleSelectLocation = this.handleSelectLocation.bind(this);
    this.requestLocation = this.requestLocation.bind(this);
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
    this.toggleFavourite = this.toggleFavourite.bind(this);
    this.handleClear = this.handleClear.bind(this);
    this.scrollToCarparkInfo = this.scrollToCarparkInfo.bind(this);
    this.resetSelectedCarpark = this.resetSelectedCarpark.bind(this);
    this.handleCarparkClicked = this.handleCarparkClicked.bind(this);
    this.updateCarparksWithinRadius = debounce(
      500,
      this.updateCarparksWithinRadius
    );
  }

  componentDidMount() {
    if (!this.state.user) {
      auth.onAuthStateChanged(user => {
        if (user) {
          this.setState({ user });
          this.retrieveFavouritedCarparks();
        }
      });
    }

    // Retrieve carparks from backend and update localStorage
    Axios.get(
      `${process.env.REACT_APP_BACKEND_API}public/carpark-availability/latest`
    ).then(response => {
      if (response.status === 200) {
        const carparks = mapEntriesToCarparks(response.data, false);
        this.setState({
          carparks,
        });

        localStorage.setItem(LOCAL_STORAGE_CARPARKS, JSON.stringify(carparks));
        console.log("Backend: everything", carparks);
      }
    });
    this.updateCarparksWithinRadius(
      this.state.location,
      this.state.radius,
      this.state.address
    );
  }

  retrieveFavouritedCarparks() {
    const { currentUser } = auth;
    if (currentUser) {
      firebase
        .database()
        .ref(`users/${currentUser.uid}/carparks`)
        .on("value", data => {
          const favouritedCarparkIds = data.val();
          if (favouritedCarparkIds) {
            localStorage.setItem(
              LOCAL_STORAGE_FAVOURITED,
              JSON.stringify(favouritedCarparkIds)
            );
            const carparkIds = Object.keys(favouritedCarparkIds).join(",");
            Axios.get(
              `${process.env.REACT_APP_BACKEND_API}public/carpark-availability/carpark-id?carparkIds=${carparkIds}&lotTypes=C`
            ).then(response => {
              if (response.status == 200) {
                const favouritedCarparks = updateCarparksDistFromSrc(
                  mapEntriesToCarparks(Object.values(response.data), false),
                  this.state.location
                );
                this.setState({ favouritedCarparks, favouritedCarparkIds });
                console.log("Backend: favourites", favouritedCarparks);
              }
            });
          } else {
            this.setState({
              favouritedCarparks: [],
              favouritedCarparkIds: {},
            });
          }
        });
    }
  }

  updateCarparksWithinRadius(location: Point, radius: string, address: string) {
    Axios.get(
      `${process.env.REACT_APP_BACKEND_API}public/carpark-availability/nearest/queries?latitude=${location.lat}&longitude=${location.lng}&lotTypes=C&radius=${radius}`
    ).then(response => {
      var nearbyCarparks;
      if (response.status == 200) {
        nearbyCarparks = mapEntriesToCarparks(response.data, true);
        console.log("Backend: within radius", nearbyCarparks);
        //sorts by distance (nearest to furthers)
        nearbyCarparks.sort(
          (carpark1: Carpark, carpark2: Carpark) =>
            carpark1.distFromSrc - carpark2.distFromSrc
        );
        const refs: any = nearbyCarparks.reduce(
          (acc: any, carpark: Carpark) => {
            acc[carpark.carparkId] = createRef();
            return acc;
          },
          {}
        );
        this.setState({
          location,
          selectedLatLng: location,
          address,
          nearbyCarparks,
          refs,
        });
      }
    });
  }

  handleRadiusChange(event: React.ChangeEvent<HTMLInputElement>) {
    const value: string = event.target.value;
    if (value) {
      const radiusToSearch = parseInt(value) > 3000 ? "3000" : value;
      this.setState({ radius: radiusToSearch });
      this.updateCarparksWithinRadius(
        this.state.location,
        radiusToSearch,
        this.state.address
      );
    } else {
      this.setState({ radius: "" });
    }
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
      .then((location: Point) => {
        this.updateCarparksWithinRadius(
          location,
          this.state.radius,
          description
        );
      });
  }

  handleClear() {
    this.setState({
      address: " ",
    });
  }

  requestLocation() {
    if (navigator.geolocation) {
      const updatePosition = (position: Position) => {
        const { latitude, longitude } = position.coords;
        const location = {
          lat: latitude,
          lng: longitude,
        } as Point;
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
      this.setState({ user: result.user });
      console.log("signed in");
    });
  }

  toggleFavourite() {
    this.setState({ showFavourites: !this.state.showFavourites });
  }

  handleCarparkClicked(carpark: Carpark) {
    this.setState({
      selectedLatLng: {
        lat: carpark.latitude,
        lng: carpark.longitude,
      },
    });
  }

  scrollToCarparkInfo(id: string) {
    console.log("scrolling to id", id);

    if (this.state.refs[id]) {
      this.state.refs[id].current.scrollIntoView({
        behavior: "smooth",
        block: "start",
      });

      this.setState({
        selectedId: id,
      });
    }
  }

  resetSelectedCarpark() {
    this.setState({
      selectedId: undefined,
    });
  }

  renderLoginButton() {
    if (this.state.user) {
      return (
        <div className="header-login">
          <button
            type="button"
            className="btn btn-outline-secondary"
            onClick={this.logout}
          >
            Log Out
          </button>
        </div>
      );
    } else {
      return (
        <button
          type="button"
          className="btn btn-outline-secondary"
          onClick={this.login}
        >
          Log In
        </button>
      );
    }
  }

  render() {
    return (
      <div className="row no-gutters">
        <div className="col-lg-7 left-col">
          {/* Start of form */}
          <form>
            <div className="form-group">
              <label htmlFor="google-places-autocomplete-input">Location</label>
              <div className="input-group mb-2 search-bar">
                <GooglePlacesAutocomplete
                  inputClassName="form-control"
                  autocompletionRequest={{
                    componentRestrictions: { country: "sg" },
                  }}
                  initialValue={this.state.address}
                  onSelect={this.handleSelectLocation}
                />
                <div className="input-group-append">
                  <div className="clear-input" onClick={this.handleClear}>
                    <i className="fas fa-times-circle" />
                  </div>
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={this.requestLocation}
                  >
                    <i className="fa fa-location-arrow" />
                  </button>
                </div>
              </div>
              <label htmlFor="radiusInput">Search radius</label>
              <div className="radius-login-wrapper">
                <div className="input-group mb-2 radius-input">
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
                <div>{this.renderLoginButton()}</div>
              </div>
            </div>
          </form>
          {/* End of form */}

          <div className="carparks-header">
            {this.state.showFavourites ? (
              <div className="label">Showing favourites</div>
            ) : (
              <div className="label">
                {this.state.nearbyCarparks.length} carparks within radius
              </div>
            )}
            {this.state.user && (
              <i
                onClick={this.toggleFavourite}
                className={`toggle-favourite far fa-heart ${
                  this.state.showFavourites ? "active" : ""
                }`}
              ></i>
            )}
          </div>

          <CarparkList
            user={this.state.user}
            showFavourites={this.state.showFavourites}
            nearbyCarparks={this.state.nearbyCarparks}
            favouritedCarparks={this.state.favouritedCarparks}
            favouritedCarparkIds={this.state.favouritedCarparkIds}
            refs={this.state.refs}
            selectedId={this.state.selectedId}
            handleClicked={this.handleCarparkClicked}
            isOnline={true}
          />
        </div>
        <div className="map-wrapper col-lg-5">
          <CarparkMap
            handleMarkerClick={this.scrollToCarparkInfo}
            handleInfoWindowClose={this.resetSelectedCarpark}
            location={this.state.location}
            currentLatLng={this.state.selectedLatLng}
            radius={this.state.radius === "" ? 0 : parseInt(this.state.radius)}
            markers={this.state.carparks}
          />
        </div>
      </div>
    );
  }
}

export default MainPage;
