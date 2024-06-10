import { Fragment, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import Footer from '../component/layout/Footer'
import Header from '../component/layout/Header'
import PageHeader from '../component/layout/PageHeader'
import Pagination from '../component/sidebar/Pagination'
import Rating from '../component/sidebar/Rating'
import SkillSelect from '../component/sidebar/skill-select'
import { fetchAllCourses } from '../redux/action/courseAction'
import { calculateAverageRating } from '../util/mathUtils'

const CoursePage = () => {
  const dispatch = useDispatch()
  const { courses } = useSelector((state) => state.course)
  const [currentPage, setCurrentPage] = useState(1)
  const coursesPerPage = 3

  useEffect(() => {
    fetchAllCourses(dispatch)
  }, [dispatch])

  // Get current courses
  const indexOfLastCourse = currentPage * coursesPerPage
  const indexOfFirstCourse = indexOfLastCourse - coursesPerPage
  const currentCourses = courses.slice(indexOfFirstCourse, indexOfLastCourse)

  // Change page
  const handlePageChange = (pageNumber) => setCurrentPage(pageNumber)

  return (
    <Fragment>
      <Header />
      <PageHeader title={'Archives: Courses'} curPage={'Courses'} />
      <div className="course-section padding-tb section-bg">
        <div className="container">
          <div className="section-wrapper">
            <div className="course-showing-part">
              <div className="d-flex flex-wrap align-items-center justify-content-between">
                <div className="course-showing-part-left">
                  <p>
                    Showing {indexOfFirstCourse + 1}-{indexOfLastCourse} of {courses.length} results
                  </p>
                </div>
                <div className="course-showing-part-right d-flex flex-wrap align-items-center">
                  <span>Sort by :</span>
                  <div className="select-item">
                    <SkillSelect select={'all'} />
                    <div className="select-icon">
                      <i className="icofont-rounded-down"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="row g-4 justify-content-center row-cols-xl-3 row-cols-md-2 row-cols-1">
              {currentCourses.map((course, i) => (
                <div className="col" key={i}>
                  <div className="course-item">
                    <div className="course-inner">
                      <div className="course-thumb" style={{ height: '200px' }}>
                        <img src={course.thumbnail} alt={`${course.title} image`} />
                      </div>
                      <div className="course-content">
                        <div className="course-price">{course.price}</div>
                        <div className="course-category">
                          <div className="course-cate">
                            <a href="#">{course.category.name}</a>
                          </div>
                          <div className="course-reiew">
                            <Rating value={calculateAverageRating(course.reviews)} />
                            <span className="ratting-count"> {course.reviews.length} reviews</span>
                          </div>
                        </div>
                        <Link to={`/course/detail/${course.id}`}>
                          <h4>{course.name}</h4>
                        </Link>
                        <div className="course-details">
                          <div className="couse-count">
                            <i className="icofont-video-alt"></i> {course.videos.length} lessons
                          </div>
                          <div className="couse-topic">
                            <i className="icofont-signal"></i> Online Class
                          </div>
                        </div>
                        <div className="course-footer">
                          <div className="course-author" style={{ display: 'flex', alignItems: 'center' }}>
                            <img
                              src={course.instructor.avatar}
                              style={{ width: '32px', height: '32px' }}
                              alt={`Course img`}
                            />
                            <Link to="/team-single" className="ca-name">
                              {course.instructor.name}
                            </Link>
                          </div>
                          <div className="course-btn">
                            <Link
                              to={course.is_enrolled ? `/view-course/${course.id}` : `/course/detail/${course.id}`}
                              className={`${course.is_enrolled && `text-primary`} lab-btn-text`}
                            >
                              {course.is_enrolled ? <>View course</> : <>Know more</>}
                              <i
                                className={`${
                                  course.is_enrolled ? `icofont-video text-primary` : `icofont-external-link`
                                }`}
                              ></i>
                            </Link>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
            <Pagination
              currentPage={currentPage}
              totalPages={Math.ceil(courses.length / coursesPerPage)}
              onPageChange={handlePageChange}
            />
          </div>
        </div>
      </div>
      <Footer />
    </Fragment>
  )
}

export default CoursePage
