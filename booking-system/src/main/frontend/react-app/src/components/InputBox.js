import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Proptypes from 'prop-types'
import "./InputBox.css";
import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: false,
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
    }
});

const InputBox = ({ buttonText }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function validateForm() {
        return email.length > 0 && password.length > 0;
    }

    function handleSubmit(event) {
        event.preventDefault();

        instance.post(`/login`
        , {
            username: email,
            password: password
        })
    }
    axios.get("https://api.github.com/users/mapbox")
        .then((response) => {
            console.log(response.data);
            console.log(response.status);
            console.log(response.statusText);
            console.log(response.headers);
            console.log(response.config);
        });
    instance.get("/test")
        .then((response) => {
            console.log(response.data);
            console.log(response.status);
            console.log(response.statusText);
            console.log(response.headers);
            console.log(response.config);
        });
        instance.post(`/login`
        , {
            username: "u1",
            password: "p1"
        })
        .then((response) => {
            console.log(response.data);
        });

    return (<div className="auth-wrapper">
    <div className="auth-inner">
        <div className="InputBox">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="email">
                    <Form.Label>Email</Form.Label><br />
                    <Form.Control
                        autoFocus
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>Password</Form.Label><br />
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button block size="lg" type="submit" disabled={!validateForm()}>
                    {buttonText}
                </Button>
            </Form>
        </div>
        </div>
        </div>
    );
}

InputBox.defaultProps = {
    buttonText: 'Log in',
}

InputBox.propTypes = {
    buttonText: Proptypes.string,
}

export default InputBox;

/* import './InputBox.css'
import React, { Component } from "react";

export default class InputBox extends Component {
    render() {
        return (<div className="auth-wrapper">
        <div className="auth-inner">
            <form>
                <h3>Sign In</h3>

                <div className="form-group">
                    <label>Email address</label>
                    <input type="email" className="form-control" placeholder="Enter email" />
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="Enter password" />
                </div>

                <div className="form-group">
                    <div className="custom-control custom-checkbox">
                        <input type="checkbox" className="custom-control-input" id="customCheck1" />
                        <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                    </div>
                </div>

                <button type="submit" className="btn btn-primary btn-block">Submit</button>
                <p className="forgot-password text-right">
                    Forgot <a Link to={'/register'}>password?</a>
                </p>
            </form>
        </div>
      </div>
        );
    }
} */