import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link, useParams } from "react-router-dom";
import Carousel, { Dots } from '@brainhubeu/react-carousel';
import { humanReadable } from "../functions/string-utils";
import '@brainhubeu/react-carousel/lib/style.css';
import DayPicker, { DateUtils } from 'react-day-picker';

import Form from "react-validation/build/form";
import CheckButton from "react-validation/build/button";
import AuthService from "../services/auth.service";
import { isRole } from "../functions/roles";

export default class Announcement extends Component {
  constructor(props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);
    this.handleDayClick = this.handleDayClick.bind(this);
    this.state = {
      currentUser: undefined,
      content: "",
      announcementDetails: {},
      selectedDate: null,
      successful: false,
      message: ""
    };
  }

  isSelectingFirstDay(from, to, day) {
    const isBeforeFirstDay = from && DateUtils.isDayBefore(day, from);
    const isRangeSelected = from && to;
    return !from || isBeforeFirstDay || isRangeSelected;
  }

  handleDayClick(day, { selected, disabled }) {
    if (disabled)
      return;
    this.setState({
      selectedDate: selected ? undefined : day,
    });
  }

  handlePost(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      var id = undefined;
      if (this.props.match != undefined)
        id = this.props.match.params.id;
      RequestService.postReservation(
        id,
        this.state.selectedDate.getTime()
      ).then(
        response => {
          this.setState({
            message: response.data.message,
            successful: true
          });
        },
        error => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            successful: false,
            message: resMessage
          });
        }
      );
    }
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
    const { selectedDate, announcementDetails } = this.state;
    const { weekdays } = announcementDetails;
    const dateStart = new Date(announcementDetails.dateStart);
    const dateEnd = new Date(announcementDetails.dateEnd);
    var daysOfWeek = [];
    if (weekdays != undefined) {
    for (var i = 0; i < 7; i++)
      if (weekdays[i] == '0')
        daysOfWeek.push((i + 1) % 7);
    }
    var disabledDays = [{ before: new Date(), before: dateStart, after: dateEnd }, { daysOfWeek: daysOfWeek }];
    var a = this.state.announcementDetails;
    var image_list = a.images;
    if (image_list == undefined)
      return null;
    var slide_images = image_list.map(i => <img src={i} className="slideshow-image" alt={i} />);
    return (
      <div className="container">
        <header className="jumbotron">
          <Carousel plugins={['arrows']}>
            {slide_images}
          </Carousel>
          <h4>{a.title} {a.owner_id == this.state.currentUser.id ?
            <Link to={"/a/" + this.props.match.params.id + "/edit"} style={{ color: "#ddbb00" }}>(Modify)</Link> : null}</h4>
          <h4>Owner: <Link to={"/u/" + a.owner_id} style={{ color: "#00cf00" }}>{a.displayName}</Link></h4>
          <h3>Sport type: {humanReadable(a.sportType)}</h3>
          <h3>Location: {a.city + ", " + a.country}</h3>
          <h3>Price: {a.price}</h3>
          <h3>Publication date: {a.publication_date_time}</h3>
          <div className="shown-text">
            {a.description}</div>
          {/* <Dots number={slide_images.length} thumbnails={slide_images} value={this.state.value} onChange={this.onchange} number={slide_images.length} /> */}
          {isRole(this.state.currentUser, "ROLE_CLIENT") ?
            <div className="col-md-12">
              <div className="card card-container" style={{ width: "400px" }}>
                <Form
                  onSubmit={this.handlePost}
                  ref={c => {
                    this.form = c;
                  }}
                >
                  {!this.state.successful && (
                    <div>
                      <div className="form-group">
                        <div className="centered"><DayPicker
                          firstDayOfWeek={1}
                          numberOfMonths={1}
                          selectedDays={selectedDate}
                          disabledDays={disabledDays}
                          onDayClick={this.handleDayClick}
                          onDayMouseEnter={this.handleDayMouseEnter}
                        />
                        </div>
                        <div className="form-group">
                          {selectedDate ? `Selected ${selectedDate.toLocaleDateString("ro")}` : 'Please select a day.'}
                        </div>
                      </div>
                      <div className="form-group">
                        <button
                          className="btn btn-primary btn-block"
                          disabled={this.state.loading}
                        >
                          {this.state.loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                          )}
                          <span>Submit</span>
                        </button></div>
                    </div>
                  )}

                  {this.state.message && (
                    <div className="form-group">
                      <div
                        className={
                          this.state.successful
                            ? "alert alert-success"
                            : "alert alert-danger"
                        }
                        role="alert"
                      >
                        {this.state.message}
                      </div>
                    </div>
                  )}
                  <CheckButton
                    style={{ display: "none" }}
                    ref={c => {
                      this.checkBtn = c;
                    }}
                  />
                </Form>
              </div>
            </div> : null}
        </header>
      </div>
    );
  }
}
