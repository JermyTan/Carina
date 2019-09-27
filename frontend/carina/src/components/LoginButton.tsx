import React from "react";
import "styles/LoginButton.scss";

interface ILoginButtonProps {
  user: any;
  logout: () => void;
  login?: () => void;
  isOnline: boolean;
}

class LoginButton extends React.Component<ILoginButtonProps, any> {
  constructor(props: ILoginButtonProps) {
    super(props);

    this.renderLoginButton = this.renderLoginButton.bind(this);
  }

  shouldComponentUpdate(nextProps: ILoginButtonProps, nextState: any) {
    return this.props.user != nextProps.user;
  }

  renderLoginButton() {
    if (this.props.user) {
      return (
        <div className="header-login">
          <button className="button" onClick={this.props.logout}>
            Log Out
          </button>
          <div className="displayName">
            You are currently signed in as {this.props.user.displayName}.
          </div>
        </div>
      );
    } else if (this.props.isOnline) {
      return (
        <button className="button" onClick={this.props.login}>
          Log In
        </button>
      );
    }
    {
      return <div />;
    }
  }

  render() {
    return <div className="header">{this.renderLoginButton()}</div>;
  }
}

export default LoginButton;
