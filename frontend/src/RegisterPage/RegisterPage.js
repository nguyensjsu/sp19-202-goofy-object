import React, { Component } from 'react';
import axios from 'axios';
import config from '../config';
import cookie from 'react-cookies';

class RegisterPage extends Component {

    state = {
        username: "",
        password: "",
        msg: ""
    }

    onChangeHandler = (e) => {
        console.log(e);
        this.setState({
            [e.target.id]: e.target.value
        });
    }

    render() {
        return (
            <React.Fragment>
                <h1>Register</h1>

            </React.Fragment>
        )
    }

}

export default RegisterPage;