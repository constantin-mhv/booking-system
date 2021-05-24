import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link, useParams } from "react-router-dom";
import Carousel, {Dots} from '@brainhubeu/react-carousel';
import '@brainhubeu/react-carousel/lib/style.css';

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
    var slide_images = image_list.map(i => <img src={i} className="sliderimg" alt={i} class="slideshow-image"/>);
    //var slide_images = image_list.map(i => <video className="sliderimg" alt={i} class="slideshow-image"><source src={i} type="video/mp4"/></video>);
    return (
      <div className="container">
        <header className="jumbotron">
          <Carousel plugins={['arrows']}>
          {slide_images}
        </Carousel>
          <h3>{a.title}</h3>
          <h4>Owner: <Link to={"/u/" + a.owner_id} style={{color: "#00cf00"}}>{a.displayName}</Link></h4>
          <h3>Publication date: {a.publication_date_time}</h3>
          <div className="shown-text">
            {a.description}</div>
        {/* <Dots number={slide_images.length} thumbnails={slide_images} value={this.state.value} onChange={this.onchange} number={slide_images.length} /> */}
        </header>
      </div>
    );
  }
}
