import axios from 'axios';
import authHeader from './auth-header';
import AuthService from './auth.service';

const API_URL = 'http://localhost:8080/api/';

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

  getUserDetails(id) {
    return authAxios.get("users/u/" + id);
  }

  getAnnouncementListByUser(id) {
    return authAxios.get("users/u/" + id + "/list");
  }

  postNewAnnouncement(title, description, images, country, region) {
    return authAxios.post('/owner/a/new', {
      title,
      description,
      images,
      country,
      region
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
