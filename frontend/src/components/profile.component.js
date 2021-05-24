import React, { Component } from "react";
import { Link, Redirect } from "react-router-dom";
import RequestService from "../services/request.service";
import AuthService from "../services/auth.service";
import getRoleName from "../functions/roles"

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      viewedUser: { username: "" },
      announcementsList: []
    };
  }

  componentDidMount() {
    RequestService.getUserDetails(this.props.match.params.id).then(
      response => {
        this.setState({
          viewedUser: response.data
        });
      });
    RequestService.getAnnouncementListByUser(this.props.match.params.id).then(
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

    const { viewedUser } = this.state;
    var announcements = this.state.announcementsList.map(a => <li><Link to={"/a/" + a.id} style={{ color: "orange" }}>{a.title}</Link></li>);

    return (
      <div className="container">
        <div>
          <header className="jumbotron">
            <h3>
              <strong>{viewedUser.displayName}</strong> Profile
          </h3>
          </header>
          {viewedUser.roles != undefined ? <>
          <p>
            <strong>Role:</strong>{" "}
            {getRoleName(viewedUser.roles)}
          </p>
          {viewedUser.roles.toString() == "ROLE_OWNER" ?
            <>
              <strong>Announcements:</strong>
              <h3>{announcements}</h3>
            </> : null}
          </> : null }
        </div>
      </div>
    );
  }
}
