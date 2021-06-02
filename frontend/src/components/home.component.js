import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link } from "react-router-dom";
import { isRole } from "../functions/roles"
import AuthService from "../services/auth.service";
import UserService from "../services/user.service";
import AnnPrev from "../components/ann-prev.component";

export default class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: undefined,
      userReady: false,
      content: "",
      announcementsList: [],
      image: ""
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (currentUser == null)
      return;

    this.setState({ currentUser: currentUser, userReady: true })
    RequestService.getAnnouncementList().then(
      response => {
        this.setState({
          announcementsList: response.data
        });
      });
    UserService.getPublicContent().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  render() {
    const { currentUser } = this.state;
    
    if (this.state.userReady && isRole(currentUser, "ROLE_OWNER"))
      var announcements = this.state.announcementsList.map(a => AnnPrev(a, a.owner_id == currentUser.id));
    else
      var announcements = this.state.announcementsList.map(a => AnnPrev(a));
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>Announcements</h3><br/>
          {/* <h3>{this.state.content}</h3> */}
          <h3>{announcements}</h3>
        </header>
      </div>
    );
  }
}
