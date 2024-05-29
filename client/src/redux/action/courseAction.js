import axiosInstance from '../../axiosInstance'
import { API_ROUTES } from '../../constant'
import { setCourses } from '../slice/courseSlice'

export const fetchAllCourses = async (dispatch) => {
  try {
    const res = await axiosInstance.get(API_ROUTES.courses)
    if (res.data.success) {
      dispatch(setCourses(res.data.courses))
    }
  } catch (error) {
    console.log(error.response ? error.response.message : error.message)
  }
}

export const fetchCourseDetail = async (courseId, next = () => {}, errorHandle = () => {}) => {
  try {
    const res = await axiosInstance.get(`${API_ROUTES.courseDetail}?courseId=${courseId}`)
    if (res.data.success) {
      next(res.data.course)
    }
  } catch (error) {
    console.log(error)
    errorHandle(error.response ? error.response.msg : error.message)
  }
}

export const postReview = async (form, next = () => {}, errorHandle = () => {}) => {
  try {
    const res = await axiosInstance.post(API_ROUTES.createReview, form)
    if (res.data.success) {
      console.log(res.data.new_review)
      next(res.data.new_review)
    }
  } catch (error) {
    errorHandle(error.response ? error.response.data.message : error.message)
  }
}
