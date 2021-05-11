import axios from 'axios';

const API_URL = 'http://localhost:8080/api/ads/';

class AdService {

  postNewAd(title, desc) {
    return axios.post(API_URL, {
      title,
      desc
    });
  }
}

export default new AdService();
