import React, { Component } from "react";
import { Link } from "react-router-dom";
import RequestService from "../services/request.service";

function handle(id) {
    RequestService.deleteAnnouncement(id);
    window.location.reload();
};

export default function AnnPrev(a, showDelete = false) {
    return (
        <div className="form-control" style={{ display: "flex" }}><Link to={"/a/" + a.id} style={{ color: "green" }}>{a.title}</Link>
        {showDelete ? <Link style={{ color: "red", marginLeft: "auto" }} onClick={_ => handle(a.id)}>Delete</Link> : null}</div>
    );
}