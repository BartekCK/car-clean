import { deleteSafe, get, getSafe, post, postSafe } from './api';
import {
  carsUserApiUrl,
  createUserApiUrl,
  loginUserApiUrl,
  servicesApiUrl, servicesFreeHoursApiUrl, servicesUserApiUrl, basketUserApiUrl, addToUserBasketApiUrl, removeFromUserBasketApiUrl, clearBasketUserApiUrl,
  usersApiUrl, shopApiUrl, shopCategoryApiUrl, createOrderApiUrl, orderProductsUserApiUrl, changeOrderProductStatusApiUrl,
} from './routes';

export const getAllServices = () => get(servicesApiUrl());
export const getServiceById = (id) => get(servicesApiUrl(id));

export const postCreateUser = (body) => post(createUserApiUrl(), body);
export const postLoginUser = (body) => post(loginUserApiUrl(), body);

export const getUserByToken = () => getSafe(usersApiUrl());

export const getAllUserCars = () => getSafe(carsUserApiUrl());
export const getUserCars = (id) => getSafe(carsUserApiUrl(id));
export const addUserCars = (body) => postSafe(carsUserApiUrl(), body);
export const deleteUserCar = (id) => deleteSafe(carsUserApiUrl(id));


export const getFreeHoursServices = (body) => postSafe(servicesFreeHoursApiUrl(), body);
export const addReservationServices = (body) => postSafe(servicesUserApiUrl(), body);

export const getAllProducts = () => get(shopApiUrl());
export const getProductsByCategory = (category) => get(shopCategoryApiUrl(category));

export const getUserBasket = () => getSafe(basketUserApiUrl());
export const addToUserBasket = (id) => getSafe(addToUserBasketApiUrl(id));
export const deleteFromUserBasket = (id) => getSafe(removeFromUserBasketApiUrl(id));
export const clearUserBasket = () => getSafe(clearBasketUserApiUrl());

export const getUserOrderProducts = () => getSafe(orderProductsUserApiUrl());
export const createUserOrderProducts = () => getSafe(createOrderApiUrl());
export const changeStatusUserOrderProducts = (id) => getSafe(changeOrderProductStatusApiUrl(id));
