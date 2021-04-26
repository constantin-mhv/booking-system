import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Proptypes from 'prop-types'
import "./InputBox.css";
import axios from "axios";

const InputBox = ({buttonText}) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function validateForm() {
    return email.length > 0 && password.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();

    axios.post('user', {
        email: email,
        password: password
    })
  }

  return (
    <div className="InputBox">
      <Form onSubmit={handleSubmit}>
        <Form.Group size="lg" controlId="email">
          <Form.Label>Email</Form.Label><br/>
          <Form.Control
            autoFocus
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>
        <Form.Group size="lg" controlId="password">
          <Form.Label>Password</Form.Label><br/>
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