import React, { Component } from 'react';
import axios from 'axios';
import config from '../config';
import cookie from 'react-cookies';

class RegisterPage extends Component {

    state = {
        username: "",
        password: "",
        age: 0,
        gender: "",
        region: "",
        msg: ""
    }

    onChangeHandler = (e) => {
        console.log(e);
        this.setState({
            [e.target.id]: e.target.value
        });
    }

    onRegisterHandler = () => {
        axios.post(config.host + '/register', {
            username: this.state.username,
            password: this.state.password,
            age: this.state.age,
            gender: this.state.gender,
            region: this.state.region
        }).then(res => {
            if (res.status === 200) {
                console.log('Register status:', res.data);
                this.props.history.push('/login')
            }
        })
    }

    render() {
        return (
            <React.Fragment>
                <h1>Register</h1>
                <label htmlFor="username">Username: </label>
                <input type="text" id="username" value={this.state.username} onChange={this.onChangeHandler}></input><br></br>
                <label htmlFor="password">Password: </label>
                <input type="password" id="password" value={this.state.password} onChange={this.onChangeHandler}></input><br></br>
                <label htmlFor="age">Age: </label>
                <input type="number" id="age" value={this.state.age} onChange={this.onChangeHandler}></input><br></br>
                <label htmlFor="gender">Gender: </label>
                <input type="text" id="gender" value={this.state.gender} onChange={this.onChangeHandler}></input><br></br>
                <label htmlFor="region">Region: </label>
                <input type="text" id="region" value={this.state.region} onChange={this.onChangeHandler}></input><br></br>
                <input type="button" value="Register" onClick={this.onRegisterHandler}></input>
                <p>{this.state.msg}</p>
            </React.Fragment>
        )
    }

}

export default RegisterPage;