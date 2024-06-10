import { createSubmission } from '../redux/action/courseAction'

const BASE = 'http://localhost:8080/api/v1'
export const API_ROUTES = {
  // Auth
  loadUser: BASE + '/auth/load-user',
  login: BASE + '/auth/login',
  register: BASE + '/auth/register',
  requestToken: BASE + '/auth/request-token',
  confirmAccount: BASE + '/auth/confirm-account',
  // User
  updateInformation: BASE + '/user/update',
  changeAvatar: BASE + '/user/change-avatar',
  // Course
  courses: BASE + '/course',
  courseDetail: BASE + '/course/detail',
  // Enroll
  createPayment: BASE + '/enroll/create',
  executePayment: BASE + '/enroll/execute',
  getEnrollCourse: BASE + '/enroll/course',
  // Blog
  blogs: BASE + '/blog',
  blogDetail: BASE + '/blog/detail',
  // Review
  createReview: BASE + '/review/create',
  // Tracking Progress
  createTrackingProgress: BASE + '/tracking/create',
  // Certificate
  getCertificate: BASE + '/certificate/get',
  // Submission
  createSubmission: BASE + '/submission/create'
}
