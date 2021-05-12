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

class RequestService {

  getAnnouncementList() {
    return authAxios.get("announcements/list");
    }

  getAnnouncementDetails(id) {
    return authAxios.get("announcements/a/" + id);
    }

  postNewAnnouncement(title, description) {
    return authAxios.post('announcements/new', {
      title,
      description
    });
  }
}

export default new RequestService();
