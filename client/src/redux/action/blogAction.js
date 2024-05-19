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
