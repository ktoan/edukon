const BASE = 'http://localhost:8080/api/v1'
export const API_ROUTES = {
  // Auth
  loadUser: BASE + '/auth/load-user',
  login: BASE + '/auth/login',
  register: BASE + '/auth/register',
  requestToken: BASE + '/auth/request-token',
  confirmAccount: BASE + '/auth/confirm-account',
  // Course
  courses: BASE + '/course',
  courseDetail: BASE + '/course/detail',
  // Enroll
  createPayment: BASE + '/enroll/create',
  executePayment: BASE + '/enroll/execute',
  // blog
  blogs: BASE + '/blog'
}
