import axios from 'axios';

export const get = async (url) => {
  try {
    return (await axios.get(url)).data;
  } catch (e) {
    throw e;
  }
};

export const getSafe = async (url) => {
  try {
    const token = localStorage.getItem('@token');
    if (token) {
      return await axios.get(url, {
        headers: {
          authorization: `Bearer ${token}`,
        },
      });
    }
  } catch (e) {
    throw e;
  }
};

export const post = async (url, data) => {
  try {
    return await axios.post(url, data);
  } catch (e) {
    throw e;
  }
};
