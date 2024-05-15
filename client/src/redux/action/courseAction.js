import axios from 'axios'
import { API_ROUTES } from '../../constant'
import { setCourses } from '../slice/courseSlice'

export const fetchAllCourses = async (dispatch) => {
  try {
    const res = await axios.get(API_ROUTES.courses)
    if (res.data.success) {
      dispatch(setCourses(res.data.courses))
    }
  } catch (error) {
    console.log(error.response ? error.response.message : error.message)
  }
}

export const fetchCourseDetail = async (courseId, next = () => {}, errorHandle = () => {}) => {
  try {
    const res = await axios.get(`${API_ROUTES.courseDetail}?courseId=${courseId}`)
    if (res.data.success) {
      next(res.data.course, res.data.is_enrolled)
    }
  } catch (error) {
    errorHandle(error.response ? error.response.msg : error.message)
  }
}
