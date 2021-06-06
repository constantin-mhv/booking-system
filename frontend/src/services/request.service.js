import axios from 'axios';
import authHeader from './auth-header';
import AuthService from './auth.service';

const API_URL = 'http://' + window.location.hostname + ':8080/api/'

const authAxios = axios.create(
  {
    baseURL: API_URL,
    headers: authHeader()
  }
);

const basicAxios = axios.create(
  {
    baseURL: API_URL,
    headers: {}
  }
);

class RequestService {

  putAnnouncementList(sortCondition, sortTarget, country, city, sportType, priceMin, priceMax) {
    return authAxios.put("announcements/list", {
      sortCondition,
      sortTarget,
      country,
      city,
      sportType,
      priceMin,
      priceMax
    });
  }

  getAnnouncementDetails(id) {
    return authAxios.get("announcements/a/" + id);
  }

  deleteAnnouncement(id) {
    return authAxios.delete("owner/a/" + id);
  }

  changeVisibilityAnnouncement(id, visible) {
    var status;
    if (visible)
      status = "ACTIVE";
    else
      status = "HIDDEN";
    return authAxios.put("owner/a/state/" + id, {
      status
    });
  }

  getUserDetails(id) {
    return authAxios.get("users/u/" + id);
  }

  getAnnouncementListByUser(id) {
    return authAxios.get("users/u/" + id + "/list");
  }

  getReservationListByUser() {
    return authAxios.get("client/reservations");
  }
  
  postReservation(id, date) {
    return authAxios.post("client/a/book/" + id, {
      date
    });
  }

  postAnnouncement(title, description, sportType, price, images, country, city, dateStart, dateEnd, weekdays, id) {
    console.log(price);
    if (id == undefined)
      return authAxios.post("owner/a/new", {
        title,
        description,
        sportType,
        price,
        images,
        country,
        city,
        dateStart,
        dateEnd,
        weekdays
      });
    return authAxios.put("owner/a/" + id, {
      title,
      description,
      sportType,
      price,
      images,
      country,
      city
    });
  }

  // Dummy Get
  getDummy() {
    return authAxios.get("/test/debug/get");
  }

  // Dummy Post
  postDummy(json_text) {
    return authAxios.post("/test/debug/post", json_text);
  }
}

export default new RequestService();
