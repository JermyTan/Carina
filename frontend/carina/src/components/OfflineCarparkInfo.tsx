import React from "react";

import "styles/CarparkInfo.scss";
import { Carpark } from "../utils/Types";

interface IOfflineCarparkInfoState {
  isFavourited: boolean;
}

interface IOfflineCarparkInfoProps {
  carpark: Carpark;
  isFavourited: boolean;
}

class OfflineCarparkInfo extends React.Component<
  IOfflineCarparkInfoProps,
  IOfflineCarparkInfoState
> {
  constructor(props: IOfflineCarparkInfoProps) {
    super(props);
    this.state = {
      isFavourited: this.props.isFavourited,
    };
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
