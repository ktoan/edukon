import axios from 'axios'
import { API_ROUTES } from '../../constant'
import { setBlogs } from '../slice/blogSlice'

export const fetchAllBlogs = async (dispatch) => {
  try {
    const res = await axios.get(API_ROUTES.blogs)
    if (res.data.success) {
      dispatch(setBlogs(res.data.blogs))
    }
  } catch (error) {
    console.log(error.response ? error.response.message : error.message)
  }
}

export const fetchBlogDetail = async (blogId, next = () => {}, errorHandle = () => {}) => {
  try {
    const res = await axios.get(`${API_ROUTES.blogDetail}?blogId=${blogId}`)
    if (res.data.success) {
      next(res.data.blog)
    }
  } catch (error) {
    errorHandle(error.response ? error.response.msg : error.message)
  }
}
