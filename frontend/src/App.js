import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import ChessBoard from './ChessBoard/ChessBoard';

class App extends Component {
  render() {
    return (
      <div className="App">
        <ChessBoard></ChessBoard>
      </div>
    );
  }
}

export default App;
