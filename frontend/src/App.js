import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import ChessBoard from './ChessBoard/ChessBoard';
import LoginPage from './LoginPage/LoginPage'

class App extends Component {
  render() {
    return (
      <div className="App">
        <LoginPage></LoginPage>
        <ChessBoard></ChessBoard>
      </div>
    );
  }
}

export default App;
