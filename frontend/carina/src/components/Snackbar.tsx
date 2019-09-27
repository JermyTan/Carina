import React from "react";

import "../styles/Snackbar.scss";

interface ISnackbarProps {
  message: string;
}

const Snackbar: React.FC<ISnackbarProps> = ({ message }) => (
  <div className="snackbar">
    <i className="fas fa-exclamation-circle"></i>
    <span>{message}</span>
  </div>
);

export default Snackbar;
