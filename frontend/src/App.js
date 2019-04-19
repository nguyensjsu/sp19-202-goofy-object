import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

import ChessBoard from './ChessBoard/ChessBoard';
import LoginPage from './LoginPage/LoginPage'

class App extends Component {


  state = {
    loggedIn : false
  }

  onLoginHandler = (status) => {
    console.log("called")
    this.setState({
      loggedIn: status
    });
  }

  render() {
    return (
      <Router>
        <div className="App">
          <h1>Five Goofy in a Row</h1>
          <Route path="/" exact render={(props) => <LoginPage {...props} onLoginHandler={this.onLoginHandler} />} />
          {/* <Route path="/mode" exact component={LoginPage} /> */}
          {this.state.loggedIn ? <Route path="/game" exact component={ChessBoard} /> : null}
        </div>
      </Router>
    );
  }
}

export default App;
