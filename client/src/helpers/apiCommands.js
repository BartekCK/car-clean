import { get, getSafe, post } from './api';
import { createUserApiUrl, loginUserApiUrl, servicesApiUrl, usersApiUrl } from './routes';

export const getAllServices = () => get(servicesApiUrl());
export const getServiceById = (id) => get(servicesApiUrl(id));

export const postCreateUser = (body) => post(createUserApiUrl(), body);
export const postLoginUser = (body) => post(loginUserApiUrl(), body);

export const getUserByToken = () => getSafe(usersApiUrl());
