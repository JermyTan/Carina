import React from "react";
import CarparkMap from "../components/CarparkMap";

const defaultState = {
  // Fullerton Raffles Place
  location: {
    lat: 1.286194,
    lng: 103.853032,
  },
  zoom: 16,
  radius: 300,
};

class MainPage extends React.Component<any, any> {
  // TODO: Add state to keep track of location entered
  render() {
    return (
      <CarparkMap
        location={defaultState.location}
        radius={defaultState.radius}
      />
    );
  }
}

export default MainPage;
