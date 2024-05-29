import axios from 'axios'

// Create an Axios instance
const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api/v1/'
})

// Add a request interceptor
axiosInstance.interceptors.request.use(
  (config) => {
    const token = getAuthToken() // Retrieve token using the function
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Add a response interceptor if needed (optional)
axiosInstance.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    // Handle response errors here (e.g., token expiration, unauthorized access)
    return Promise.reject(error)
  }
)

const getAuthToken = () => {
  const persistRoot = localStorage.getItem('persist:root')
  if (!persistRoot) {
    return null
  }

  try {
    const parsedData = JSON.parse(persistRoot)
    if (parsedData.auth) {
      const authData = JSON.parse(parsedData.auth)
      return authData.access_token || null
    }
  } catch (error) {
    console.error('Error parsing auth token from localStorage:', error)
    return null
  }

  return null
}

export default axiosInstance
