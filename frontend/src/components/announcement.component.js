import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link, useParams } from "react-router-dom";

import UserService from "../services/user.service";

export default class Announcement extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      announcementDetails: {}
    };
  }

  componentDidMount() {
    RequestService.getAnnouncementDetails(this.props.match.params.id).then(
      response => {
        this.setState({
          announcementDetails: response.data
          //announcementDetails: {title: "Titlu", description: "Descriere", username: "George", owner_id: "gigel3qi292"}
        });
      });
  }

  render() {
    var a = this.state.announcementDetails;
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>{a.title}</h3>
          <h4>Owner: <Link to={"/u/" + a.owner_id} style={{color: "#00cf00"}}>{a.username}</Link></h4>
          <div className="shown-text">
            {a.description}</div>
        </header>
      </div>
    );
  }
}
