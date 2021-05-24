import React from 'react';
import ReactDOM from 'react-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import Textarea from "react-validation/build/textarea";
import CheckButton from "react-validation/build/button";
import RequestService from "../services/request.service";
import { CountryDropdown, RegionDropdown, CountryRegionData } from 'react-country-region-selector';

// const API_URL = 'http://localhost:8080/api/text/';

const required = value => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const chracterCount = (value, min, max) => {
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
    this.state = {
      title: "",
      desc: "",
      images: "",
      successful: false,
      message: "",
      country: "Romania",
      region: "Bucuresti"
    };
  }

  myChangeHandler = (event) => {
    this.setState({ [event.target.name]: event.target.value });
  }

  myChangeHandlerDirect = (name, value) => {
    this.setState({ [name]: value });
  }

  handlePost(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      RequestService.postNewAnnouncement(
        this.state.title,
        this.state.desc,
        this.state.images.split(/\r\n|\n|\r/).map(value => value.trim()).filter(value => value != ""),
        this.state.country,
        this.state.region
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

  render() {
    return (
      <div className="col-md-12">
        <div className="card card-container" style={{ width: "700px" }}>

          <Form
            onSubmit={this.handlePost}
            ref={c => {
              this.form = c;
            }}
          >
            <label className="form-title">Publish a new announcement</label>
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="title">Title</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="title"
                    onChange={this.myChangeHandler}
                    validations={[required, value => chracterCount(value, 4, 120)]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="desc">Description</label>
                  <Textarea style={{ height: "250px" }}
                    className="form-control"
                    name="desc"
                    onChange={this.myChangeHandler}
                    validations={[required, value => chracterCount(value, 0, 4000)]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="images">Image Links</label>
                  <Textarea style={{ height: "100px" }}
                    className="form-control"
                    name="images"
                    placeholder="Input image links, one on each line."
                    onChange={this.myChangeHandler}
                    validations={[required, value => chracterCount(value, 0, 3000)]}
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
      </div >
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