import axios from 'axios';
import authHeader from './auth-header';
import AuthService from './auth.service';

const API_URL = 'http://188.26.149.159:8080/api/';

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

  getAnnouncementList() {
    return authAxios.get("announcements/list/all");
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

  getReservationsListByUser(id) {
    return authAxios.get("client/reservations");
  }

  postAnnouncement(title, description, sportType, images, country, city, price, id) {
    if (id == undefined)
      return authAxios.post('/owner/a/new', {
        title,
        description,
        sportType,
        images,
        country,
        city,
        price
      });
    return authAxios.put('/owner/a/' + id, {
      title,
      description,
      sportType,
      images,
      country,
      city,
      price
    });
  }

  // Dummy Get
  getDummy() {
    return authAxios.get('/test/debug/get');
  }

  // Dummy Post
  postDummy(json_text) {
    return authAxios.post('/test/debug/post', json_text);
  }
}

export default new RequestService();
