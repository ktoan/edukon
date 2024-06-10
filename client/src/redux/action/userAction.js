import axiosInstance from '../../axiosInstance'
import { API_ROUTES } from '../../constant'
import { changeAvatar, setUser } from '../slice/authSlice'

export const updateUserInformation = async (dispatch, form, next, errorHandle) => {
  try {
    const res = await axiosInstance.post(API_ROUTES.updateInformation, form)
    if (res.data.success) {
      dispatch(setUser(res.data.updated_user))
      next()
    }
  } catch (error) {
    errorHandle(error.response ? error.response.message : error.message)
  }
}

export const changeUserAvatar = async (dispatch, form, next = () => {}, errorHandle = () => {}) => {
  try {
    const res = await axiosInstance.post(API_ROUTES.changeAvatar, form)
    if (res.data.success) {
      dispatch(changeAvatar(res.data.new_avatar))
      next()
    }
  } catch (error) {
    errorHandle(error.response ? error.response.msg : error.message)
  }
}
