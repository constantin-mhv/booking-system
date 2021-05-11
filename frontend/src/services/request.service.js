import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/';

const authAxios = axios.create(
  {
    baseURL: API_URL,
    headers: authHeader()
  }
);
const user = JSON.parse(localStorage.getItem('user'));

class RequestService {

  postNewAnnouncement(title, description) {
    return authAxios.post('announcements/new', {
      title,
      description
    });
  }
}

export default new RequestService();
