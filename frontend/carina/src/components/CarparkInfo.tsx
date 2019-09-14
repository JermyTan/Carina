import React from "react";

import "styles/CarparkInfo.scss";
import ExpandMoreSvg from "./svgrs/ExpandMoreSvg";
import ExpandLessSvg from "./svgrs/ExpandLessSvg";
import DirectionsSvg from "./svgrs/DirectionsSvg";

interface ICarparkInfoState {
  isExpanded: boolean;
}

interface ICarparkInfoProps {
  location: {
    lat: number;
    lng: number;
  };
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
    };

    this.handleExpand = this.handleExpand.bind(this);
  }

  handleExpand() {
    this.setState({ isExpanded: !this.state.isExpanded });
  }

  render() {
    const redirectionUrl = [
      GOOGLE_MAP_REDIR_URL_PREFIX,
      this.props.location.lat,
      ",",
      this.props.location.lng,
    ].join("");
    return (
      <div className="info-wrapper">
        <div className="info card">
          <div className="card-body d-flex no-gutters justify-content-between">
            <div className="carpark-addresses">
              <h5 className="card-title">Carpark Address</h5>
              <h6 className="card-subtitle text-muted">Carpark subtitle</h6>
            </div>
            <div className="carpark-lots">
              <div>342</div>
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
            <div className="card-body">
              Show histogram of lots remaining here
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
