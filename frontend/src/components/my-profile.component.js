import React, { Component } from "react";
import { Link, Redirect } from "react-router-dom";
import RequestService from "../services/request.service";
import AuthService from "../services/auth.service";

export default class MyProfile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { username: "" },
      announcementsList: []
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser) this.setState({ redirect: "/home" });
    this.setState({ currentUser: currentUser, userReady: true })
    RequestService.getAnnouncementListByUser(currentUser.id).then(
      response => {
        this.setState({
          announcementsList: response.data
        });
      });
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />
    }

    const { currentUser } = this.state;
    var announcements = this.state.announcementsList.map(a => <li><Link to={"/a/" + a.id} style={{color: "orange"}}>{a.title}</Link></li>);

    return (
      <div className="container">
        {(this.state.userReady) ?
        <div>
        <header className="jumbotron">
          <h3>
            <strong>{currentUser.username}</strong> Profile
          </h3>
        </header>
        <p>
          <strong>Token:</strong>{" "}
          {currentUser.accessToken.substring(0, 20)} ...{" "}
          {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
        </p>
        <p>
          <strong>Id:</strong>{" "}
          {currentUser.id}
        </p>
        <p>
          <strong>Email:</strong>{" "}
          {currentUser.email}
        </p>
        <strong>Authorities:</strong>
        <ul>
          {currentUser.roles &&
            currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
        </ul>
        <strong>Announcements:</strong>
          <h3>{announcements}</h3>
      </div>: null}
      </div>
    );
  }
}
