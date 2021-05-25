import React, { Component } from "react";
import { Link, Redirect } from "react-router-dom";
import RequestService from "../services/request.service";
import AuthService from "../services/auth.service";
import { getRoleName } from "../functions/roles"
import AnnPrev from "../components/ann-prev.component";

export default class MyProfile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { displayName: "" },
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
    var announcements = this.state.announcementsList.map(a => AnnPrev(a, true));

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
            {currentUser.roles.toString() == "ROLE_OWNER" ?
              <>
                <strong>Announcements:</strong>
                <h3>{announcements}</h3>
              </> : null}
          </div> : null}
      </div>
    );
  }
}
