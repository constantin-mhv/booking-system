import React, { Component } from "react";
import Form from "react-validation/build/form";
import Textarea from "react-validation/build/textarea";
import CheckButton from "react-validation/build/button";
import RequestService from "../services/request.service";

export default class DebugComponent extends Component {
  constructor(props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);
    this.state = {
      message: "",
      successful: false,
      json_get: "",
      json_post: ""
    };
  }

  componentDidMount() {
    RequestService.getDummy().then(
      response => {
        this.setState({
          json_get: JSON.stringify(response.data)
        });
      },
      error => {
        this.setState({
          json_get:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  myChangeHandler = (event) => {
    this.setState({ [event.target.name]: event.target.value });
  }

  handlePost(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      RequestService.postDummy(
        this.state.json_post
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
            <label className="form-title">Test Get and Post</label>
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="json_get">Get</label>
                  <div style={{color: "blue"}}>
                    {this.state.json_get}
                  </div>
                </div>
                <div className="form-group">
                  <label htmlFor="json_post">Post</label>
                  <Textarea style={{ height: "250px" }}
                    className="form-control"
                    name="json_post"
                    onChange={this.myChangeHandler}
                  />
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
      </div>
    );
  }
}
