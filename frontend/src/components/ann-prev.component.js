import React, { Component } from "react";
import { Link } from "react-router-dom";
import RequestService from "../services/request.service";

function handleToggleVisibility(id, visible) {
    RequestService.changeVisibilityAnnouncement(id, visible);
    window.location.reload();
};

function handleDelete(id) {
    RequestService.deleteAnnouncement(id);
    window.location.reload();
};

export default function AnnPrev(a, owned = false) {
    return (
        <div className="form-control" style={{ display: "flex" }}><Link to={"/a/" + a.id} style={{ color: "green" }}>{a.title}</Link>
        {owned ? <div style={{marginLeft: "auto" }}><Link style={{ color: "grey"}} onClick={_ => handleToggleVisibility(a.id, false)}>Hide</Link>
        {"   "}<Link style={{ color: "red"}} onClick={_ => handleDelete(a.id)}>Delete</Link></div> : null}</div>
    );
}