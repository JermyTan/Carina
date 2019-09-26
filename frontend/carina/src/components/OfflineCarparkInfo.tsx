import React from "react";

import firebase, { auth } from "../firebase";
import "styles/CarparkInfo.scss";
import star from "../svgs/favourite.svg";
import starFilled from "../svgs/favourited.svg";
import { Carpark } from "../utils/Types";

interface IOfflineCarparkInfoState {
  isFavourited: boolean;
}

interface IOfflineCarparkInfoProps {
  carpark: Carpark;
  showFavourite: boolean;
  isFavourited: boolean;
}

class OfflineCarparkInfo extends React.Component<
  IOfflineCarparkInfoProps,
  IOfflineCarparkInfoState
> {
  constructor(props: IOfflineCarparkInfoProps) {
    super(props);
    this.state = {
      isFavourited: this.props.isFavourited
    };

    this.handleFavourite = this.handleFavourite.bind(this);
  }

  handleFavourite() {
    if (!this.state.isFavourited) {
      //Add carparkId to firebase
      this.addToFavourites();
    } else {
      //Remove carparkId from firebase
      this.removeFromFavourites();
    }
  }

  addToFavourites() {
    const { currentUser } = auth;
    if (currentUser) {
      firebase
        .database()
        .ref(
          `users/${currentUser.uid}/carparks/${this.props.carpark.carparkId}`
        )
        .set(true)
        .then(() => {
          console.log("Added to favourites");
          this.setState({ isFavourited: !this.state.isFavourited });
        })
        .catch(() => {
          console.log("Error while adding to favourites");
        });
    }
  }

  removeFromFavourites() {
    const { currentUser } = auth;
    if (currentUser) {
      firebase
        .database()
        .ref(
          `users/${currentUser.uid}/carparks/${this.props.carpark.carparkId}`
        )
        .remove()
        .then(() => {
          console.log("Removed from favourites");
          this.setState({ isFavourited: !this.state.isFavourited });
        })
        .catch(() => {
          console.log("Error while removing from favourites");
        });
    }
  }

  classifyLotCount(availableLots: number) {
    if (availableLots >= 100) return "high";
    if (availableLots >= 30) return "med";
    return "low";
  }

  render() {
    return (
      <div className="info-wrapper">
        <div className="info card">
          <div className="card-body d-flex no-gutters justify-content-between">
            <div className="carpark-addresses">
              <h5 className="card-title">{this.props.carpark.address}</h5>
              <h6 className="card-subtitle text-muted">
                {this.props.carpark.subAddress}
              </h6>
            </div>
            {this.props.showFavourite && (
              <div className="favourite ">
                <img
                  className="favourite-icon"
                  src={this.state.isFavourited ? starFilled : star}
                  onClick={this.handleFavourite}
                />
              </div>
            )}
            <div className="carpark-lots">
              <div
                className={`lot-count ${this.classifyLotCount(
                  parseInt(this.props.carpark.availableLots)
                )}`}
              >
                {this.props.carpark.availableLots}
              </div>
              <div className="card-subtitle text-muted">lots left</div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default OfflineCarparkInfo;
