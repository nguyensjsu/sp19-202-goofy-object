import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Link, Redirect, Switch } from 'react-router-dom';

import ChessBoard from './ChessBoard/ChessBoard';
import LoginPage from './LoginPage/LoginPage';
import ModeSelection from './ModeSelection/ModeSelection';

class App extends Component {


  state = {
    loggedIn: false
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
          <Switch>
            <Route path="/login" exact render={(props) => <LoginPage {...props} onLoginHandler={this.onLoginHandler} />} />
            <Route path="/mode" exact component={ModeSelection} />
            {this.state.loggedIn ? <Route path="/game" exact component={ChessBoard} /> : null}
            {this.state.loggedIn ? null : <Redirect to="/login"></Redirect>}
          </Switch>
        </div>
      </Router>
    );
  }
}

export default App;
