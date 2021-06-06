import React from 'react';
import ReactDOM from 'react-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import Select from "react-validation/build/select";
import Textarea from "react-validation/build/textarea";
import CheckButton from "react-validation/build/button";
import RequestService from "../services/request.service";
import { humanReadable } from "../functions/string-utils";
import { CountryDropdown, RegionDropdown } from 'react-country-region-selector';
import Helmet from 'react-helmet';
import DayPicker, { DateUtils } from 'react-day-picker';

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

const weekdayNames = Object.freeze([
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
  "SUNDAY"
]);

const required = value => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const requiredDate = ({ from, to, weekdays }) => {
  if (from == undefined || to == undefined)
    return (
      <div className="alert alert-danger" role="alert">
        The interval has not been selected!
      </div>
    );
  if (!weekdays.includes("1"))
    return (
      <div className="alert alert-danger" role="alert">
        At least one day of the week must be selected!
      </div>
    );
};

const characterCount = (value, min, max) => {
  if (value.length < min) {
    return (
      <div className="alert alert-danger" role="alert">
        Must contain at least {min} characters!
      </div>
    );
  }
  if (value.length > max) {
    return (
      <div className="alert alert-danger" role="alert">
        Must contain at most {max} characters!
      </div>
    );
  }
};

export default class NewAnn extends React.Component {
  constructor(props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);
    this.handleDayClick = this.handleDayClick.bind(this);
    this.handleDayMouseEnter = this.handleDayMouseEnter.bind(this);
    this.handleResetClick = this.handleResetClick.bind(this);
    this.state = {
      title: "",
      desc: "",
      sportType: sportTypes[0],
      price: "0.0",
      images: "",
      country: "Romania",
      region: "Bucuresti",
      from: null,
      to: null,
      enteredTo: null,
      weekdays: "1111111".split(''),
      successful: false,
      message: ""
    };
  }

  isSelectingFirstDay(from, to, day) {
    const isBeforeFirstDay = from && DateUtils.isDayBefore(day, from);
    const isRangeSelected = from && to;
    return !from || isBeforeFirstDay || isRangeSelected;
  }

  handleDayClick(day, { disabled }) {
    if (disabled)
      return;
    const { from, to } = this.state;
    if (from && to && day >= from) {
      this.setState({
        to: day,
        enteredTo: day,
      });
    } else if (this.isSelectingFirstDay(from, to, day)) {
      this.setState({
        from: day,
        to: null,
        enteredTo: null,
      });
    } else {
      this.setState({
        to: day,
        enteredTo: day,
      });
    }
  }

  handleDayMouseEnter(day) {
    const { from, to } = this.state;
    if (!this.isSelectingFirstDay(from, to, day)) {
      this.setState({
        enteredTo: day,
      });
    }
  }

  handleResetClick() {
    this.setState({
      from: null,
      to: null,
      enteredTo: null
    });
  }

  myChangeHandler = (event) => {
    console.log(event);
    this.setState({ [event.target.name]: event.target.value });
    console.log(this.state);
  }

  myChangeHandlerDirect = (name, value) => {
    console.log(name, value);
    this.setState({ [name]: value });
    console.log(this.state.weekdays);
  }

  myChangeHandlerDay = (index, value) => {
    var aux = this.state.weekdays;
    aux[index] = value;
    this.setState({ weekdays: aux });
    console.log(this.state.weekdays);
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
      RequestService.postAnnouncement(
        this.state.title,
        this.state.desc,
        this.state.sportType,
        parseFloat(this.state.price),
        this.state.images.split(/\r\n|\n|\r/).map(value => value.trim()).filter(value => value != ""),
        this.state.country,
        this.state.region,
        this.state.from.getTime(),
        this.state.to.getTime(),
        this.state.weekdays.join(''),
        id
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
    if (this.props.match != undefined && this.props.match.params.id != undefined)
      RequestService.getAnnouncementDetails(this.props.match.params.id).then(
        response => {
          console.log(response.data);
          this.setState({
            title: response.data.title,
            desc: response.data.description,
            sportType: response.data.sportType,
            price: response.data.price.toString(),
            images: response.data.images.toString().replaceAll(",", "\n"),
            country: response.data.country,
            region: response.data.city,
            from: new Date(response.data.dateStart),
            to: new Date(response.data.dateEnd),
            weekdays: response.data.weekdays,
            id: this.props.match.params.id
          });
        });
  }

  render() {
    const { from, to, enteredTo, weekdays } = this.state;
    const modifiers = { start: from, end: enteredTo };
    const disabledDays = { before: this.state.from, before: new Date() };
    const selectedDays = [from, { from, to: enteredTo }];
    var weekdaysSeparate = [];
    for (var i = 0; i < 7; i++) {
      const weekdayTag = weekdayNames[i].toLocaleLowerCase();
      const weekdayName = humanReadable(weekdayNames[i]);
      weekdaysSeparate[i] =
        <>
          <label htmlFor={weekdayTag}>
            <input
              type="checkbox"
              className="form-checkbox"
              name={i}
              checked={weekdays[i] == "1"}
              onChange={(e) => {
                this.myChangeHandlerDay(e.target.name, e.target.checked ? "1" : "0")
              }}
            >
            </input>{weekdayName}</label>
        </>;
    }
    return (
      <div className="col-md-12">
        <div className="card card-container" style={{ width: "700px" }}>

          <Form
            onSubmit={this.handlePost}
            ref={c => {
              this.form = c;
            }}
          >
            {(this.state.id == undefined) ? <label className="form-title">Publish a new announcement</label> :
              <label className="form-title">Modify announcement</label>}
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="title">Title</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="title"
                    value={this.state.title}
                    onChange={this.myChangeHandler}
                    validations={[required, value => characterCount(value, 4, 120)]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="desc">Description</label>
                  <Textarea style={{ height: "250px" }}
                    className="form-control"
                    name="desc"
                    value={this.state.desc}
                    onChange={this.myChangeHandler}
                    validations={[required, value => characterCount(value, 0, 4000)]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="sportType">Sport Type</label>
                  <Select
                    className="form-control"
                    name="sportType"
                    value={this.state.sportType}
                    onChange={this.myChangeHandler}
                  >
                    {sportTypes.map(s => <option value={s}>{humanReadable(s)}</option>)}
                  </Select>
                </div>
                <div className="form-group">
                  <label htmlFor="price">Price</label>
                <Input
                    type="text"
                    pattern="[0-9]*.[0-9]*"
                    className="form-control"
                    name="price"
                    value={this.state.price}
                    onChange={this.myChangeHandler}
                    validations={[required]}
                  />
                  </div>
                <div className="form-group">
                  <label htmlFor="images">Image Links</label>
                  <Textarea style={{ height: "100px" }}
                    className="form-control"
                    name="images"
                    value={this.state.images}
                    placeholder="Input image links, one on each line."
                    onChange={this.myChangeHandler}
                    validations={[required, value => characterCount(value, 0, 3000)]}
                  />
                </div>
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
                <div className="form-group" id="sameline_container">
                  <div id="sameline_block1">
                    <DayPicker
                      firstDayOfWeek={1}
                      className="Range"
                      numberOfMonths={1}
                      fromMonth={from}
                      selectedDays={selectedDays}
                      disabledDays={disabledDays}
                      modifiers={modifiers}
                      onDayClick={this.handleDayClick}
                      onDayMouseEnter={this.handleDayMouseEnter}
                    />
                  </div>
                  <div id="sameline_block2">
                    {weekdaysSeparate}
                  </div>
                </div>
                <div className="form-group">
                  {!from && !to && 'Please select the first day.'}
                  {from && !to && 'Please select the last day.'}
                  {from &&
                    to &&
                    `Selected from ${from.toLocaleDateString("ro")} to
                        ${to.toLocaleDateString("ro")}`}{' '}
                  {from && to && (
                    <button className="btn btn-primary" onClick={this.handleResetClick}>
                      Reset
                    </button>
                  )}
                  <Helmet>
                    <style>{`
          .Range .DayPicker-Day--selected:not(.DayPicker-Day--start):not(.DayPicker-Day--end):not(.DayPicker-Day--outside) {
            background-color: #f0f8ff !important;
            color: #4a90e2;
          }
          .Range .DayPicker-Day {
            border-radius: 0 !important;
          }
        `}</style>
                  </Helmet>
                </div>
                <Input
                  hidden="true"
                  disabled="true"
                  type="none"
                  className="form-control"
                  validations={[_ => requiredDate(this.state)]}
                />
                <div className="form-group">
                  <button
                    className="btn btn-primary btn-block"
                    disabled={this.state.loading}
                  >
                    {this.state.loading && (
                      <span className="spinner-border spinner-border-sm"></span>
                    )}
                    <span>Submit</span>
                  </button>
                </div>
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
      </div>
    );
    /* return (
      <form>
      <h1> Publish a new ad</h1>
      <p>Title:</p>
      <input
        type='text'
        name='title'
        onChange={this.myChangeHandler}
      />
      <p>Description:</p>
      <input
        type='text'
        name='desc'
        onChange={this.myChangeHandler}
      />
      </form>
    ); */
  }
}

ReactDOM.render(<NewAnn />, document.getElementById('root'));