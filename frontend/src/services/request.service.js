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
    return authAxios.get("announcements/list");
    }

  getAnnouncementDetails(id) {
    console.log("announcements/a/" + id);
    return authAxios.get("announcements/a/" + id);
    }
    
  getUserDetails(id) {
    return authAxios.get("users/u/" + id);
    }
    
  getAnnouncementListByUser(id) {
    return authAxios.get("users/u/" + id + "/list");
    }

  postNewAnnouncement(title, description) {
    return authAxios.post('announcements/new', {
      title,
      description
    });
  }
}

export default new RequestService();
