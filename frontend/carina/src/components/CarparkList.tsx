import React from "react";
import { Carpark } from "../utils/Types";
import CarparkInfo from "./CarparkInfo";
import OfflineCarparkInfo from "./OfflineCarparkInfo";
import "styles/CarparkList.scss";

interface ICarparkListProps {
  user: any;
  showFavourites: boolean;
  nearbyCarparks: Carpark[];
  favouritedCarparks: Carpark[];
  favouritedCarparkIds: object;
  refs?: any;
  selectedId?: string;
  handleClicked?: any;
  isOnline: boolean;
}

class CarparkList extends React.Component<ICarparkListProps, any> {
  constructor(props: ICarparkListProps) {
    super(props);

    this.renderCarparks = this.renderCarparks.bind(this);
  }

  shouldComponentUpdate(nextProps: ICarparkListProps, nextState: any) {
    if (this.props.user != nextProps.user) {
      return true;
    }
    if (this.props.showFavourites != nextProps.showFavourites) {
      return true;
    } else if (this.props.showFavourites) {
      if (this.props.favouritedCarparkIds != nextProps.favouritedCarparkIds) {
        return true;
      }
    } else if (this.props.nearbyCarparks != nextProps.nearbyCarparks) {
      return true;
    }
    return false;
  }

  renderCarparks() {
    const carparksToShow: Carpark[] = this.props.showFavourites
      ? this.props.favouritedCarparks
      : this.props.nearbyCarparks;
    console.log("Render carparks", carparksToShow);
    if (this.props.isOnline) {
      return carparksToShow.map(carpark => (
        <CarparkInfo
          key={carpark.carparkId}
          handleClicked={this.props.handleClicked}
          selectedOnMap={
            this.props.selectedId !== undefined &&
            this.props.selectedId === carpark.carparkId
          }
          innerRef={this.props.refs[carpark.carparkId]}
          carpark={carpark}
          showFavourite={this.props.user != null}
          isFavourited={carpark.carparkId in this.props.favouritedCarparkIds}
        />
      ));
    } else {
      return carparksToShow.map(carpark => (
        <OfflineCarparkInfo
          key={carpark.carparkId}
          carpark={carpark}
          isFavourited={carpark.carparkId in this.props.favouritedCarparkIds}
        />
      ));
    }
  }

  render() {
    return <div className="carparks">{this.renderCarparks()}</div>;
  }
}

export default CarparkList;
