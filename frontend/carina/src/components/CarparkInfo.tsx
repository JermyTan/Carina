import React from "react";

import firebase, { auth } from "../firebase";
import "styles/CarparkInfo.scss";
import ExpandMoreSvg from "./svgrs/ExpandMoreSvg";
import ExpandLessSvg from "./svgrs/ExpandLessSvg";
import DirectionsSvg from "./svgrs/DirectionsSvg";
import HistogramChart, { HistogramData } from "./HistogramChart";
import { Carpark } from "../utils/Types";

interface ICarparkInfoState {
  isExpanded: boolean;
  isFavourited: boolean;
}

interface ICarparkInfoProps {
  carpark: Carpark;
  showFavourite: boolean;
  isFavourited: boolean;
  selectedOnMap: boolean;
  innerRef: any;
}

const GOOGLE_MAP_REDIR_URL_PREFIX =
  "http://maps.google.com/maps?z=12&t=m&q=loc:";

class CarparkInfo extends React.Component<
  ICarparkInfoProps,
  ICarparkInfoState
> {
  constructor(props: ICarparkInfoProps) {
    super(props);
    this.state = {
      isExpanded: false,
      isFavourited: this.props.isFavourited,
    };

    this.handleExpand = this.handleExpand.bind(this);
    this.handleFavourite = this.handleFavourite.bind(this);
  }

  handleExpand() {
    this.setState({ isExpanded: !this.state.isExpanded });
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
    const redirectionUrl = [
      GOOGLE_MAP_REDIR_URL_PREFIX,
      this.props.carpark.latitude,
      "+",
      this.props.carpark.longitude,
    ].join("");
    return (
      <div ref={this.props.innerRef} className="info-wrapper">
        <div
          className={`info card ${this.props.selectedOnMap ? "selected" : ""}`}
        >
          <div className="card-body d-flex no-gutters justify-content-between">
            <div className="carpark-addresses">
              <h5 className="card-title">{this.props.carpark.address}</h5>
              <h6 className="card-subtitle text-muted">
                {this.props.carpark.subAddress}
              </h6>
            </div>
            {this.props.showFavourite && (
              <div className="favourite" onClick={this.handleFavourite}>
                {this.state.isFavourited ? (
                  <i className="fas fa-heart" />
                ) : (
                  <i className="far fa-heart" />
                )}
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
          <div className="card-body d-flex no-gutters justify-content-between elab-wrapper">
            <div className="d-flex flex-column">
              <span>Price</span>
              {/* TODO: If no time range given, we default to 1 hour, if there is we calculate total price */}
              <span>$13.75</span>
              {/* TODO: Show how we derived at price */}
              <span>Price breakdown</span>
            </div>

            <div className="d-flex flex-column distance-info">
              <span>Dist</span>
              <span>{this.props.carpark.distFromSrc} m</span>
              <a
                href={redirectionUrl}
                target="_blank"
                rel="noopener noreferrer"
              >
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

export default React.forwardRef((props: ICarparkInfoProps, ref) => (
  <CarparkInfo innerRef={ref} {...props} />
));
