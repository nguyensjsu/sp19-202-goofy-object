import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

import ChessBoard from './ChessBoard/ChessBoard';
import LoginPage from './LoginPage/LoginPage'

class App extends Component {
  render() {
    return (
      <Router>
        <div className="App">
          <h1>Five Goofy in a Row</h1>
          <Route path="/" exact component={LoginPage} />
          {/* <Route path="mode" exact component={LoginPage} /> */}
          <Route path="/game" exact component={ChessBoard} />
        </div>
      </Router>
    );
  }
}

export default App;
