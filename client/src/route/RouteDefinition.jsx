import React from 'react'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { ScrollToTop } from '../component/layout'
import {
  AboutPage,
  BlogPage,
  ContactPage,
  CoursePage,
  CourseSinglePage,
  CourseViewPage,
  EnrollPage,
  EnrollPaypalPage,
  Error404,
  ErrorPage,
  HomePage,
  LoginPage,
  ProfilePage,
  SignUpPage
} from '../page'
import VerifyAccount from '../page/VerifyAccount'
import ProtectAuthenticatedRoute from './ProtectAuthenticatedRoute'

const RouteDefinition = () => {
  return (
    <Router>
      <ScrollToTop />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/course" element={<CoursePage />} />
        <Route path="/view-course/:courseId" element={<CourseViewPage />} />
        <Route path="/course/detail/:courseId" element={<CourseSinglePage />} />
        <Route path="/enroll/:courseId" element={<EnrollPage />} />
        <Route path="/enroll/paypal" element={<EnrollPaypalPage />} />
        <Route path="/blog" element={<BlogPage />} />
        <Route path="/contact" element={<ContactPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route element={<ProtectAuthenticatedRoute />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/sign-up" element={<SignUpPage />} />
          <Route path="/verify-account" element={<VerifyAccount />} />
        </Route>
        <Route path="/failed" element={<ErrorPage />} />
        <Route path="*" element={<Error404 />} />
      </Routes>
    </Router>
  )
}

export default RouteDefinition
