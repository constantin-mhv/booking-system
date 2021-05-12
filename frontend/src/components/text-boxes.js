import React from 'react';
import ReactDOM from 'react-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import Textarea from "react-validation/build/textarea";
import CheckButton from "react-validation/build/button";
import RequestService from "../services/request.service";


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

const minimumCharacters = value => {
  if (value.length < 10) {
    return (
      <div className="alert alert-danger" role="alert">
        Must contain at least 10 characters!
      </div>
    );
  }
};

export default class MyForm extends React.Component {
  constructor(props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);
    this.state = {
      title: "",
      desc: "",
      successful: false,
      message: ""
    };
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
      RequestService.postNewAnnouncement(
        this.state.title,
        this.state.desc
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
        <div className="card card-container">

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
                    validations={[required]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="desc">Description</label>
                  <Textarea
                    className="form-control"
                    name="desc"
                    onChange={this.myChangeHandler}
                    validations={[required/* , minimumCharacters */]}
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

ReactDOM.render(<MyForm />, document.getElementById('root'));