const serverUrl = 'http://192.168.8.100:8080/api/v1';

export const servicesApiUrl = (id) => id ? `${serverUrl}/services/${id}` : `${serverUrl}/services`;

export const createUserApiUrl = () => `${serverUrl}/signup`;
export const loginUserApiUrl = () => `${serverUrl}/signin`;

export const usersApiUrl = (id) => id ? `${serverUrl}/users/${id}` : `${serverUrl}/users`;

export const carsUserApiUrl = (id) => id ? `${serverUrl}/users/cars/${id}` : `${serverUrl}/users/cars`;

export const servicesEmployeeApiUrl = (id) => id ? `${serverUrl}/employees/services/${id}` : `${serverUrl}/employees/services`;

export const servicesUserApiUrl = (id) => id ? `${serverUrl}/users/services/${id}` : `${serverUrl}/users/services`;
export const servicesFreeHoursApiUrl = () => `${serverUrl}/users/services/hours`;

export const mailApiUrl = () => `${serverUrl}/mails`;
