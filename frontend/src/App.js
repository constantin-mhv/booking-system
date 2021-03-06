import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";
import MyProfile from "./components/my-profile.component";
import BoardUser from "./components/board-user.component";
import BoardModerator from "./components/board-moderator.component";
import BoardAdmin from "./components/board-admin.component";
import NewAnn from "./components/new-ann.component";
import Announcement from "./components/announcement.component";
import DebugComponent from "./components/debug.component";
import Calendar from "./components/calendar.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser, showModeratorBoard, showAdminBoard } = this.state;

    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/"} style={{color: "lightgreen"}} className="navbar-brand">
            Hack & S/ash Booking
          </Link>
          {currentUser ? (
            <div className="navbar-nav ml-auto">
              {currentUser.roles.toString() == "ROLE_OWNER" ?
              <li className="nav-item">
                <a href="/new-announcement" className="nav-link">
                  New announcement
                </a>
              </li>
              : null}
              <li className="nav-item">
                <Link to={"/my-profile"} className="nav-link">
                  {currentUser.displayName}
                </Link>
              </li>
              <li className="nav-item">
                <a href="/login" className="nav-link" onClick={this.logOut}>
                  Log out
                </a>
              </li>
            </div>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  Log in
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/register"} className="nav-link">
                  Sign up
                </Link>
              </li>
            </div>
          )}
        </nav>

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/home"]} component={Home} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/my-profile" component={MyProfile} />
            <Route path="/user" component={BoardUser} />
            <Route path="/mod" component={BoardModerator} />
            <Route path="/admin" component={BoardAdmin} />
            <Route path="/debug" component={DebugComponent} />
            <Route path="/new-announcement" component={NewAnn} />
            <Route path="/a/:id/edit" component={NewAnn} />
            <Route path="/a/:id" component={Announcement} />
            <Route path="/u/:id" component={Profile} />
            <Route path="/calendar" component={Calendar} />
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
