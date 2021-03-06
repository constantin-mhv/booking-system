import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link } from "react-router-dom";

import UserService from "../services/user.service";

export default class BoardUser extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      announcementsList: []
    };
  }

  componentDidMount() {
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
    
    var items = this.state.announcementsList.map(a => <li><Link to={"/a/" + a.id} style={{color: "orange"}}>{a.title}</Link></li>)
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>Announcements</h3>
          {/* <h3>{this.state.content}</h3> */}
          <h3>{items}</h3>
        </header>
      </div>
    );
  }
}
