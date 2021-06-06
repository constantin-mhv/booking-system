import React, { Component } from "react";
import { Link } from "react-router-dom";

export default function ResPrev(a) {
    return (
        <div className="form-control" style={{ display: "flex" }}><Link to={"/a/" + a.id} style={{ color: "green" }}>{a.title}</Link>
        <div style={{marginLeft: "auto" }}><span style={{ color: "blue"}}>{a.price}€</span>{"   "}
        <span style={{ color: "grey"}}>{new Date(a.date).toLocaleDateString("ro")}</span></div></div>
    );
}