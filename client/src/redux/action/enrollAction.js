import axios from 'axios'
import { API_ROUTES } from '../../constant'

export const createPayment = async (form, next, errorHandle) => {
  try {
    const res = await axios.post(API_ROUTES.createPayment, form)
    if (res.data.success) {
      next(res.data.success, res.data.return_url)
    }
  } catch (error) {
    errorHandle(error.response ? error.response.data.message : error.message)
  }
}

export const executePayment = async (form, next, errorHandle) => {
  try {
    const res = await axios.post(API_ROUTES.executePayment, form)
    if (res.data.success && res.data.is_enrolled) {
      next()
    }
  } catch (error) {
    errorHandle(error.response ? error.response.data.message : error.message)
  }
}
