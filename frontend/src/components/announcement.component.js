import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link, useParams } from "react-router-dom";
import Carousel, {Dots} from '@brainhubeu/react-carousel';
import { humanReadable } from "../functions/string-utils";
import '@brainhubeu/react-carousel/lib/style.css';
import Calendar from "../components/calendar.component";

import AuthService from "../services/auth.service";
import { isRole } from "../functions/roles";

function isVideo(address) {
  if (address.endsWith(".mp4"))
    return true;
  return false;
}

export default class Announcement extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: undefined,
      content: "",
      announcementDetails: {}
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    this.setState({ currentUser: currentUser, userReady: true })
    RequestService.getAnnouncementDetails(this.props.match.params.id).then(
      response => {
        console.log(response.data);
        this.setState({
          announcementDetails: response.data
        });
      });
  }

  render() {
    var a = this.state.announcementDetails;
    var image_list = a.images;
    if (image_list == undefined)
      return null;
    var slide_images = image_list.map(i => <>{isVideo(i) ? <><video className="sliderimg" alt={i} class="slideshow-image"><source src={i} type="video/mp4"/></video></> : <img src={i} className="slideshow-image" alt={i}/>}</>);
    return (
      <div className="container">
        <header className="jumbotron">
          <Carousel plugins={['arrows']}>
          {slide_images}
        </Carousel>
          <h4>{a.title} {a.owner_id == this.state.currentUser.id ?
            <Link to={"/a/" + this.props.match.params.id + "/edit"} style={{color: "#ddbb00"}}>(Modify)</Link>: null}</h4>
          <h4>Owner: <Link to={"/u/" + a.owner_id} style={{color: "#00cf00"}}>{a.displayName}</Link></h4>
          <h3>Sport type: {humanReadable(a.sportType)}</h3>
          <h3>Location: {a.city + ", " + a.country}</h3>
          <h3>Publication date: {a.publication_date_time}</h3>
          <div className="shown-text">
            {a.description}</div>
        {/* <Dots number={slide_images.length} thumbnails={slide_images} value={this.state.value} onChange={this.onchange} number={slide_images.length} /> */}
        {isRole(this.state.currentUser, "ROLE_CLIENT") ? <Calendar/> : null}
        </header>
      </div>
    );
  }
}
