const serverUrl = 'http://192.168.8.100:8080/api/v1';

export const servicesApiUrl = id => id ? `${serverUrl}/services/${id}` : `${serverUrl}/services`;
