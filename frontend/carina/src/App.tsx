import React from "react";
import MainPage from "./pages/Main";
import OfflinePage from "./pages/Offline";
//import Axios from "axios";

interface IAppState {
  isOnline: boolean;
  isLoading: boolean;
}

class App extends React.Component<any, IAppState> {
  constructor(props: any) {
    super(props);

    this.state = {
      isOnline: navigator.onLine,
      isLoading: true,
    };

    this.handleConnection = this.handleConnection.bind(this);
  }

  componentDidMount() {
    window.addEventListener("online", this.handleConnection);
    window.addEventListener("offline", this.handleConnection);
    this.handleConnection();
  }

  handleConnection() {
    if (navigator.onLine) {
      fetch(`${process.env.REACT_APP_BACKEND_API}public/version`, {
        method: "HEAD",
        mode: "no-cors",
      })
        .then((response: any) => {
          return response && (response.ok || response.type === "opaque");
        })
        .then((isOnline: boolean) => {
          this.setState({ isOnline, isLoading: false });
        });
    } else {
      this.setState({ isOnline: false, isLoading: false });
    }
  }

  renderPage() {
    if (this.state.isLoading) {
      return <div />;
    } else {
      return this.state.isOnline ? <MainPage /> : <OfflinePage />;
    }
  }

  render() {
    return <div className="App">{this.renderPage()}</div>;
  }
}

export default App;
