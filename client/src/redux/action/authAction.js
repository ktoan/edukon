import axios from "axios";
import { API_ROUTES } from "../../constant";
import { setUser, setAccessToken } from "../slice/authSlice";

export const loginUser = async (
  dispatch,
  form,
  next = () => {},
  errorHandle = () => {}
) => {
  try {
    const res = await axios.post(API_ROUTES.login, form);
    if (res.data.success) {
      dispatch(setAccessToken(res.data.access_token));
      dispatch(setUser(res.data.user));
      next();
    }
  } catch (error) {
    console.log(error);
    errorHandle(error);
  }
};

export const logoutUser = (dispatch) => {
  dispatch(setUser(null));
  dispatch(setAccessToken(null));
};

export const loadUser = async (dispatch) => {
  try {
    const res = await axios.get(API_ROUTES.loadUser);
    if (res.data.success) {
      dispatch(setUser(res.data.user));
    }
  } catch (error) {
    dispatch(setAccessToken(null));
    dispatch(setUser(null));
  }
};

export const registerNewUser = async (
  form,
  next = () => {},
  errorHandle = () => {}
) => {
  try {
    const res = await axios.post(API_ROUTES.register, form);
    if (res.data.success) {
      next(res.data.message);
    }
  } catch (error) {
    errorHandle(error.response ? error.response.data.message : error.message);
  }
};

export const sendVerifyAccountToken = async (
  email,
  next = () => {},
  errorHandle = () => {}
) => {
  try {
    const res = await axios.post(`${API_ROUTES.requestToken}?email=${email}`);
    if (res.data.success) {
      next(res.data.message);
    }
  } catch (error) {
    errorHandle(error.response ? error.response.message : error.message);
  }
};

export const confirmAccountRegistration = async (
  form,
  next = () => {},
  errorHandle = () => {}
) => {
  try {
    const res = await axios.post(API_ROUTES.confirmAccount, form);
    if (res.data.success) {
      next(res.data.message);
    }
  } catch (error) {
    errorHandle(error.response ? error.response.message : error.message);
  }
};
