import React from "react";

import firebase, { auth } from "../firebase";
import "styles/CarparkInfo.scss";
import ExpandMoreSvg from "./svgrs/ExpandMoreSvg";
import ExpandLessSvg from "./svgrs/ExpandLessSvg";
import DirectionsSvg from "./svgrs/DirectionsSvg";
import star from "../svgs/favourite.svg";
import starFilled from "../svgs/favourited.svg";
import HistogramChart, { HistogramData } from "./HistogramChart";

interface ICarparkInfoState {
  isExpanded: boolean;
  isFavourited: boolean;
}

interface ICarparkInfoProps {
  id: string;
  location: {
    lat: number;
    lng: number;
  };
  address: string;
  subAddress: string;
  numLots: string;
  showFavourite: boolean;
  isFavourited: boolean;
}

const GOOGLE_MAP_REDIR_URL_PREFIX =
  "https://www.google.com/maps/search/?api=1&query=";

class CarparkInfo extends React.Component<
  ICarparkInfoProps,
  ICarparkInfoState
> {
  constructor(props: ICarparkInfoProps) {
    super(props);
    this.state = {
      isExpanded: false,
      isFavourited: this.props.isFavourited
    };

    this.handleExpand = this.handleExpand.bind(this);
    this.handleFavourite = this.handleFavourite.bind(this);
  }

  handleExpand() {
    this.setState({ isExpanded: !this.state.isExpanded });
  }

  handleFavourite() {
    if (!this.state.isFavourited) {
      //Add carpark to firebase
      this.addToFavourites();
    } else {
      //Remove carpark from firebase
      this.removeFromFavourites();
    }
  }

  addToFavourites() {
    const { currentUser } = auth;
    if (currentUser) {
      firebase
        .database()
        .ref(`users/${currentUser.uid}/carparks/${this.props.id}`)
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
        .ref(`users/${currentUser.uid}/carparks/${this.props.id}`)
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

  render() {
    const redirectionUrl = [
      GOOGLE_MAP_REDIR_URL_PREFIX,
      this.props.location.lat,
      ",",
      this.props.location.lng
    ].join("");
    return (
      <div className="info-wrapper">
        <div className="info card">
          <div className="card-body d-flex no-gutters justify-content-between">
            <div className="carpark-addresses">
              <h5 className="card-title">{this.props.address}</h5>
              <h6 className="card-subtitle text-muted">
                {this.props.subAddress}
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
              <div>{this.props.numLots}</div>
              <div className="card-subtitle text-muted">lots left</div>
            </div>
          </div>
          <div className="card-body d-flex no-gutters justify-content-between elab-wrapper">
            <div className="d-flex flex-column">
              <span>Price</span>
              {/* TODO: If no time range given, we default to 1 hour, if there is we calculate total price */}
              <span>$13.75</span>
              {/* TODO: Show how we derived at price */}
              <span>Price breakdown</span>
            </div>

            <div className="d-flex flex-column">
              <span>Dist</span>
              <span>123 m</span>
              <a href={redirectionUrl}>
                <DirectionsSvg />
              </a>
            </div>
          </div>
          {this.state.isExpanded && (
            <div className="card-body lot-histogram">
              <HistogramChart data={HistogramData} />
            </div>
          )}
          <div className="expansion-wrapper" onClick={this.handleExpand}>
            {this.state.isExpanded ? <ExpandLessSvg /> : <ExpandMoreSvg />}
          </div>
        </div>
      </div>
    );
  }
}

export default CarparkInfo;
