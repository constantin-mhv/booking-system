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

function SimpleGet () {
    instance.get("/test")
        .then((response) => {
             console.log(response.data);
        })
        .catch(error => {
            ""
        })
}

const InputBox = ({ buttonText }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function validateForm() {
        return email.length > 0 && password.length > 0;
    }

    function handleSubmit(event) {
        //setEmail("CONSTANTIN");

        event.preventDefault();

        //setPassword(axios.get(`${API_URL}`));
        /* , {
            email: email,
            password: password
        }) */
    }
    axios.get("https://api.github.com/users/mapbox")
        .then((response) => {
            console.log(response.data);
            console.log(response.status);
            console.log(response.statusText);
            console.log(response.headers);
            console.log(response.config);
        });

    return (
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
    );
}

InputBox.defaultProps = {
    buttonText: 'Log in',
}

InputBox.propTypes = {
    buttonText: Proptypes.string,
}

export default InputBox;