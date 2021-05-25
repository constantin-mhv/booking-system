import React, { Component } from "react";
import { Link } from "react-router-dom";

export default function AnnPrev(a, showDelete = false) {
    return (
        <div className="form-control" style={{ display: "flex" }}><Link to={"/a/" + a.id} style={{ color: "green" }}>{a.title}</Link>
        {showDelete ? <Link style={{ color: "red", marginLeft: "auto" }} onClick={_ => this.handle(a.id)}>Delete</Link> : null}</div>
    );
}