import React, { Component } from "react";
import { Link, Redirect } from "react-router-dom";
import RequestService from "../services/request.service";
import AuthService from "../services/auth.service";
import { getRoleName, isRole } from "../functions/roles"
import AnnPrev from "../components/ann-prev.component";

export default class MyProfile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { displayName: "" },
      profileList: []
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser) this.setState({ redirect: "/home" });
    this.setState({ currentUser: currentUser, userReady: true })
    if (isRole(currentUser, "ROLE_OWNER")) {
      RequestService.getAnnouncementListByUser(currentUser.id).then(
        response => {
          this.setState({
            profileList: response.data
          });
        });
    }
    else if (isRole(currentUser, "ROLE_CLIENT")) {
      RequestService.getReservationListByUser(currentUser.id).then(
        response => {
          this.setState({
            profileList: response.data
          });
        });
    }
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />
    }

    const { currentUser, profileList } = this.state;/* 
    while (currentUser.roles == undefined) {}
      if (currentUser.roles.toString() == "ROLE_OWNER")
        profileList = this.state.profileList.map(a => AnnPrev(a, true));
      else if (isRole(currentUser, "ROLE_CLIENT"))
        profileList = this.state.profileList.map(a => AnnPrev(a, true)); */
    return (
      <div className="container">
        {(this.state.userReady) ?
          <div>
            <header className="jumbotron">
              <h3>
                <strong>{currentUser.displayName}</strong> Profile
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
              {currentUser.username}
            </p>
            <p>
              <strong>Role:</strong>{" "}
              {getRoleName(currentUser.roles)}
            </p>
            {isRole(currentUser, "ROLE_OWNER") ?
              <>
                <strong>Announcements:</strong>
                <h3>{profileList.map(a => AnnPrev(a, true))}</h3>
              </> : null}
            {isRole(currentUser, "ROLE_CLIENT") ?
              <>
                <strong>Reservations:</strong>
                <h3>{profileList.map(a => AnnPrev(a, false))}</h3>
              </> : null}
          </div> : null}
      </div>
    );
  }
}
