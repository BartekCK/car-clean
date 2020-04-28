const serverUrl = 'http://192.168.8.100:8080/api/v1';

export const servicesApiUrl = (id) => id ? `${serverUrl}/services/${id}` : `${serverUrl}/services`;

export const createUserApiUrl = () => `${serverUrl}/signup`;
export const loginUserApiUrl = () => `${serverUrl}/signin`;

export const usersApiUrl = (id) => id ? `${serverUrl}/users/${id}` : `${serverUrl}/users`;
