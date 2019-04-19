import React, { Component } from 'react';
import axios from 'axios';
import config from '../config';

class LoginPage extends Component {

    state = {
        username: "",
        msg: ""
    }

    onChangeHandler = (e) => {
        console.log(e);
        this.setState({
            [e.target.id]: e.target.value
        });
    }

    onLoginHandler = () => {
        console.log("Username:", this.state.username);
        axios.post(config.host + "/login", {
            username: this.state.username
        }).then(res => {
            if (res.status === 200) {
                console.log('Login status:', res.data);
                this.props.onLoginHandler(res.data);
                if (res.data) {
                    this.props.history.push('/game')
                } else {
                    this.setState({
                        msg: "Failed to login"
                    })
                }
            }
        })
    }

    render() {
        return (
            <React.Fragment>
                <h1>Login</h1>
                <label htmlFor="username">Username: </label>
                <input type="text" id="username" value={this.state.username} onChange={this.onChangeHandler}></input>
                <input type="button" value="Login" onClick={this.onLoginHandler}></input>
                <p>{this.state.msg}</p>
            </React.Fragment>

        );
    }
}

export default LoginPage;
