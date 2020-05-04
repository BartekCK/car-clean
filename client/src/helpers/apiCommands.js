import { deleteSafe, get, getSafe, post, postSafe, putSafe } from './api';
import {
  addShippingApiUrl,
  addToUserBasketApiUrl,
  basketUserApiUrl,
  carsUserApiUrl,
  changeOrderProductStatusApiUrl,
  clearBasketUserApiUrl,
  createOrderApiUrl,
  createUserApiUrl,
  loginUserApiUrl,
  mailApiUrl,
  opinionApiUrl,
  orderProductsUserApiUrl,
  payAcceptApiUrl,
  payApiUrl,
  removeFromUserBasketApiUrl,
  servicesApiUrl,
  servicesEmployeeApiUrl,
  servicesFreeHoursApiUrl,
  servicesUserApiUrl,
  shopApiUrl,
  shopCategoryApiUrl,
  usersApiUrl,
} from './routes';

export const getAllServices = () => get(servicesApiUrl());
export const getServiceById = (id) => get(servicesApiUrl(id));

export const postCreateUser = (body) => post(createUserApiUrl(), body);
export const postLoginUser = (body) => post(loginUserApiUrl(), body);

export const postSendMail = (body) => post(mailApiUrl(), body);

export const getUserByToken = () => getSafe(usersApiUrl());

export const getAllUserCars = () => getSafe(carsUserApiUrl());
export const getUserCars = (id) => getSafe(carsUserApiUrl(id));
export const addUserCars = (body) => postSafe(carsUserApiUrl(), body);
export const deleteUserCar = (id) => deleteSafe(carsUserApiUrl(id));

export const getFreeHoursServices = (body) => postSafe(servicesFreeHoursApiUrl(), body);
export const getAllUserServices = () => getSafe(servicesUserApiUrl());
export const getUserServicesById = (id) => getSafe(servicesUserApiUrl(id));
export const addReservationServices = (body) => postSafe(servicesUserApiUrl(), body);

export const getAllProducts = () => get(shopApiUrl());
export const getProductsByCategory = (category) => get(shopCategoryApiUrl(category));

export const getUserBasket = () => getSafe(basketUserApiUrl());
export const addToUserBasket = (id) => getSafe(addToUserBasketApiUrl(id));
export const deleteFromUserBasket = (id) => getSafe(removeFromUserBasketApiUrl(id));
export const clearUserBasket = () => getSafe(clearBasketUserApiUrl());


export const getAllEmployeeServicesByDay = (body) => postSafe(servicesEmployeeApiUrl(), body);
export const putServiceStatusById = (id,body) => putSafe(servicesEmployeeApiUrl(id), body);

export const addOpinionByUser = (body) => postSafe(opinionApiUrl(), body);
export const getAllOpinions = () => get(opinionApiUrl());

export const getUserOrderProducts = () => getSafe(orderProductsUserApiUrl());
export const getUserOrderProductsById = (id) => getSafe(orderProductsUserApiUrl(id));
export const createUserOrderProducts = () => getSafe(createOrderApiUrl());

export const changeStatusUserOrderProducts = (id) => getSafe(changeOrderProductStatusApiUrl(id));

export const payForOrder = (body) => postSafe(payApiUrl(), body);
export const acceptPay = (paymentId,PayerID) => getSafe(payAcceptApiUrl(paymentId,PayerID));

export const addShipping = (id, body) => postSafe(addShippingApiUrl(id), body);
