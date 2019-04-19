import React, { Component } from 'react';
import axios from 'axios';

class LoginPage extends Component {
    username = "abc"
    attempt = 5;

    state = {
        username: "",
        password: ""
    }

    onChangeHandler = (e) => {
        console.log(e);
        this.setState({
            [e.target.id]: e.target.value
        });
    }

    onLoginHandler = () => {
        console.log("Username:", this.state.username);
        console.log("Password:", this.state.password);
        this.props.onLoginHandler(true);
        this.props.history.push('/game')
    }

    render() {
        return (
            <React.Fragment>
                <h1>Login</h1>
                <input type="text" id="username" value={this.state.username} onChange={this.onChangeHandler}></input>
                <br></br>
                <input type="text" id="password" value={this.state.password} onChange={this.onChangeHandler}></input>
                <br></br>
                <input type="button" value="Login" onClick={this.onLoginHandler}></input>
            </React.Fragment>

        );
    }
}

export default LoginPage;
