import axios from 'axios';

export const get = async (url) => {
  try{
    return (await axios.get(url)).data;
  }catch (e) {
    throw e;
  }
};
