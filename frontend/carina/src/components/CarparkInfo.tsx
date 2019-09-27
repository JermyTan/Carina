import React from "react";

import firebase, { auth } from "../firebase";
import "styles/CarparkInfo.scss";
import ExpandMoreSvg from "./svgrs/ExpandMoreSvg";
import ExpandLessSvg from "./svgrs/ExpandLessSvg";
import DirectionsSvg from "./svgrs/DirectionsSvg";
import star from "../svgs/favourite.svg";
import starFilled from "../svgs/favourited.svg";
import HistogramChart, { HistogramData } from "./HistogramChart";
import { Carpark } from "../utils/Types";
import Axios from "axios";

interface ICarparkInfoState {
  isExpanded: boolean;
  isFavourited: boolean;
  histogramData: any;
}

interface ICarparkInfoProps {
  carpark: Carpark;
  showFavourite: boolean;
  isFavourited: boolean;
  selectedOnMap: boolean;
  innerRef: any;
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
      isFavourited: this.props.isFavourited,
      histogramData: HistogramData
    };

    this.handleExpand = this.handleExpand.bind(this);
    this.handleFavourite = this.handleFavourite.bind(this);
    this.updateHistogramData = this.updateHistogramData.bind(this);
    this.toggleExpand = this.toggleExpand.bind(this);
  }

  updateHistogramData() {
    const currentDay = new Date().getDay();
    Axios.get(
      `${process.env.REACT_APP_BACKEND_API}public/carpark-availability/statistics?carpark_id=${this.props.carpark.carparkId}&days=${currentDay}&lotTypes=C`
    ).then(response => {
      if (response.status == 200) {
        const data: any = response.data[0];
        const histogram: any = [];
        for (let i = 0; i < data.length; i++) {
          let temp = data[i];
          let hourlyData = {
            hour: parseInt(temp.hour),
            timeLabel: temp.timeLabel,
            lots: parseInt(temp.availableLots)
          };
          histogram.push(hourlyData);
        }
        console.log(histogram);
        this.setState({ histogramData: histogram });
      }
    });
  }

  toggleExpand() {
    this.handleExpand().then(() =>
      this.setState({ isExpanded: !this.state.isExpanded })
    );
  }

  async handleExpand() {
    if (!this.state.isExpanded) {
      this.updateHistogramData();
    }
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
      this.props.carpark.longitude,
      ",",
      this.props.carpark.latitude
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
          <div className="card-body d-flex no-gutters justify-content-end elab-wrapper">
            <div className="d-flex distance-info">
              <span>{this.props.carpark.distFromSrc} m</span>
              <a
                className="redirection-icon"
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
              <HistogramChart data={this.state.histogramData} />
            </div>
          )}
          <div className="expansion-wrapper" onClick={this.toggleExpand}>
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
