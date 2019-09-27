import React from "react";
import {
  LOCAL_STORAGE_CARPARKS,
  LOCAL_STORAGE_FAVOURITED
} from "../utils/Constants";
import LoginButton from "../components/LoginButton";
import CarparkList from "../components/CarparkList";
import { Carpark } from "../utils/Types";
import { auth } from "../firebase";
import "styles/Main.scss";

interface IOfflinePageState {
  currentSearch: string;
  showFavourites: boolean;
  carparks: Carpark[];
  favouritedCarparks: Carpark[];
  favouritedCarparksIds: Object;
  carparksToShow: Carpark[];
  user: any;
}

class OfflinePage extends React.Component<any, IOfflinePageState> {
  constructor(props: any) {
    super(props);

    this.state = {
      currentSearch: "",
      showFavourites: false,
      carparks: [],
      favouritedCarparks: [],
      favouritedCarparksIds: {},
      carparksToShow: [],
      user: null
    };

    this.requestSearch = this.requestSearch.bind(this);
    this.retrieveFavouritedCarparks = this.retrieveFavouritedCarparks.bind(
      this
    );
    this.toggleFavourite = this.toggleFavourite.bind(this);
    this.logout = this.logout.bind(this);
    this.handleSearchChange = this.handleSearchChange.bind(this);
    this.handleClear = this.handleClear.bind(this);
  }

  componentDidMount() {
    auth.onAuthStateChanged(user => {
      if (user) {
        this.setState({ user });
        this.retrieveFavouritedCarparks();
      }
    });

    //retrieve from local storage
    const allCarparks = localStorage.getItem(LOCAL_STORAGE_CARPARKS);

    if (allCarparks) {
      const carparks = JSON.parse(allCarparks);
      this.setState({
        carparks,
        carparksToShow: carparks
      });
      console.log("Local storage: everything", carparks);
    }
  }

  retrieveFavouritedCarparks() {
    const favouritedCarparksIds = localStorage.getItem(
      LOCAL_STORAGE_FAVOURITED
    );
    if (favouritedCarparksIds) {
      const carparkIds = JSON.parse(favouritedCarparksIds);
      const favouritedCarparks = this.state.carparks.filter(
        (carpark: Carpark) => carpark.carparkId in carparkIds
      );
      this.setState({ favouritedCarparks, favouritedCarparksIds: carparkIds });
    }
  }

  logout() {
    auth.signOut().then(() => {
      this.setState({ user: null, showFavourites: false });
      console.log("signed out");
    });
  }

  requestSearch() {
    const input = this.state.currentSearch.trim().toLowerCase();
    if (input) {
      const carparksToShow = this.state.carparks.filter((carpark: Carpark) => {
        return (
          carpark.address.toLowerCase().includes(input) ||
          carpark.subAddress.toLowerCase().includes(input)
        );
      });
      this.setState({ carparksToShow });
    }
  }

  handleSearchChange(event: React.ChangeEvent<HTMLInputElement>) {
    const value: string = event.target.value;
    this.setState({ currentSearch: value });
  }

  toggleFavourite() {
    this.setState({ showFavourites: !this.state.showFavourites });
  }

  handleClear() {
    this.setState({
      currentSearch: "",
      carparksToShow: this.state.carparks
    });
  }

  render() {
    return (
      <div className="row no-gutters">
        <div className="col-lg-7 left-col">
          <div className="header">
            <LoginButton
              user={this.state.user}
              logout={this.logout}
              isOnline={false}
            />
          </div>
          {/* Start of form */}
          <form>
            <div className="form-group">
              <label htmlFor="carparkInput">Search carparks</label>
              <div className="input-group mb-2 search-bar">
                <input
                  className="form-control"
                  id="carparkInput"
                  value={this.state.currentSearch}
                  onChange={this.handleSearchChange}
                />
                <div className="input-group-append">
                  <div className="clear-input" onClick={this.handleClear}>
                    <i className="fa fa-times-circle" />
                  </div>
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={this.requestSearch}
                  >
                    <i className="fa fa-search" />
                  </button>
                </div>
              </div>
            </div>
          </form>
          {/* End of form */}

          <section className="carparks-header">
            {!this.state.showFavourites && (
              <div className="label">
                {this.state.carparksToShow.length} carparks
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
          <CarparkList
            user={this.state.user}
            showFavourites={this.state.showFavourites}
            nearbyCarparks={this.state.carparksToShow}
            favouritedCarparks={this.state.favouritedCarparks}
            favouritedCarparkIds={this.state.favouritedCarparksIds}
            isOnline={false}
          />
        </div>
      </div>
    );
  }
}

export default OfflinePage;
