import React, { Component } from "react";
import RequestService from "../services/request.service";
import { Link } from "react-router-dom";
import { isRole } from "../functions/roles"
import AuthService from "../services/auth.service";
import UserService from "../services/user.service";
import AnnPrev from "../components/ann-prev.component";
import Select from "react-validation/build/select";
import Form from "react-validation/build/form";
import { humanReadable } from "../functions/string-utils";
import CheckButton from "react-validation/build/button";
import { CountryDropdown, RegionDropdown } from 'react-country-region-selector';
import { wait } from "@testing-library/dom";

const sportTypes = Object.freeze([
  "FITNESS",
  "SWIMMING",
  "SOCCER",
  "TENNIS",
  "BASEBALL",
  "GOLF",
  "RUNNING",
  "VOLLEYBALL",
  "BADMINTON",
  "SWIMMING",
  "BOXING",
  "TABLE_TENNIS",
  "SKIING",
  "ICE_SKATING",
  "ROLLER_SKATING",
  "CRICKET",
  "RUGBY",
  "POOL",
  "DARTS",
  "FOOTBALL",
  "BOWLING",
  "ICE_HOCKEY",
  "SURFING",
  "KARATE",
  "HORSE_RACING",
  "SNOWBOARDING",
  "SKATEBOARDING",
  "CYCLING",
  "ARCHERY",
  "FISHING",
  "GYMNASTICS",
  "FIGURE_SKATING",
  "ROCK_CLIMBING",
  "SUMO_WRESTLING",
  "TAEKWONDO",
  "FENCING",
  "WATER_SKIING",
  "JET_SKIING",
  "WEIGHT_LIFTING",
  "SCUBA_DIVING",
  "JUDO",
  "WIND_SURFING",
  "KICKBOXING",
  "SKY_DIVING",
  "HANG_GLIDING",
  "BUNGEE_JUMPING"
].sort().concat("OTHER"));

export default class Home extends Component {
  constructor(props) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);

    this.state = {
      currentUser: undefined,
      userReady: false,
      content: "",
      ascendingOrder: false,
      sortTarget: "date",
      country: undefined,
      region: undefined,
      sportType: null,
      priceMin: null,
      priceMax: null,
      announcementsList: []
    };
  }

  refreshList() {
    RequestService.putAnnouncementList(
      this.state.ascendingOrder ? "ASC" : null,
      this.state.sortTarget != "date" ? this.state.sortTarget : null,
      this.state.country == undefined ? null : this.state.country,
      this.state.region == undefined ? null : this.state.region,
      this.state.sportType,
      this.state.priceMin,
      this.state.priceMax
    ).then(
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
  myChangeHandler = (event) => {
    console.log(event);
    this.setState({ [event.target.name]: event.target.value });
    console.log(this.state);
  }

  myChangeHandlerDirect = (name, value) => {
    console.log(name, value);
    this.setState({ [name]: value });
  }

  onSubmit = (e) => {
    e.preventDefault();
    this.refreshList();
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (currentUser == null)
      return;

    this.setState({ currentUser: currentUser, userReady: true });

    this.refreshList();
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
          <Form
            onSubmit={this.onSubmit}
            ref={c => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="order">
                    <input
                      type="checkbox"
                      className="form-checkbox"
                      name="ascendingOrder"
                      checked={this.state.ascendingOrder}
                      onChange={(e) => {
                        this.myChangeHandlerDirect(e.target.name, e.target.checked)
                      }}
                    >
                    </input>Ascending Order</label>
                  <label htmlFor="orderDate">
                    <input
                      type="radio"
                      className="form-checkbox"
                      name="sortTarget"
                      checked={this.state.sortTarget == "date"}
                      onChange={(e) => {
                        this.myChangeHandlerDirect(e.target.name, "date")
                      }}
                    >
                    </input>Date</label>
                  <label htmlFor="orderTitle">
                    <input
                      type="radio"
                      className="form-checkbox"
                      name="sortTarget"
                      checked={this.state.sortTarget == "title"}
                      onChange={(e) => {
                        this.myChangeHandlerDirect(e.target.name, "title")
                      }}
                    >
                    </input>Title</label>
                  <label htmlFor="orderPrice">
                    <input
                      type="radio"
                      className="form-checkbox"
                      name="sortTarget"
                      checked={this.state.sortTarget == "price"}
                      onChange={(e) => {
                        this.myChangeHandlerDirect(e.target.name, "price")
                      }}
                    >
                    </input>Price</label>
                  {/* <div className="form-group">
                    <label htmlFor="sportType">Sport Type</label>
                    <Select
                      className="form-control"
                      name="sportType"
                      value={this.state.sportType}
                      onChange={this.myChangeHandler}
                    >
                      {sportTypes.map(s => <option value={s}>{humanReadable(s)}</option>)}
                    </Select>
                  </div> */}
                  <div className="form-group">
                    <CountryDropdown
                      className="form-control"
                      name="country"
                      value={this.state.country}
                      onChange={value => this.myChangeHandlerDirect("country", value)} />
                  </div>
                  <div className="form-group">
                    <RegionDropdown
                      className="form-control"
                      name="region"
                      country={this.state.country}
                      value={this.state.region}
                      onChange={value => this.myChangeHandlerDirect("region", value)} />
                  </div>
                </div>
                <div className="form-group">
                  <button
                    className="btn btn-primary"
                    disabled={this.state.loading}
                  >
                    {this.state.loading && (
                      <span className="spinner-border spinner-border-sm"></span>
                    )}
                    <span>Submit</span>
                  </button>
                </div>
              </div>
            )}{this.state.message && (
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
          <h3>Announcements</h3><br />
          {/* <h3>{this.state.content}</h3> */}
          <h3>{announcements}</h3>
        </header>
      </div>
    );
  }
}
