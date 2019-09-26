import React from "react";
import MainPage from "./pages/Main";
import OfflinePage from "./pages/Offline";

interface IAppState {
  isOnline: boolean;
}

class App extends React.Component<any, IAppState> {
  constructor(props: any) {
    super(props);

    this.state = {
      isOnline: false
    };
  }

  componentDidMount() {
    this.setState({ isOnline: navigator.onLine });
  }

  render() {
    return (
      <div className="App">
        {this.state.isOnline ? <MainPage /> : <OfflinePage />}
      </div>
    );
  }
}

export default App;
