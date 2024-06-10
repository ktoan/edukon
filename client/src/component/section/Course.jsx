import { Link } from 'react-router-dom'
import Rating from '../sidebar/Rating'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react'
import { fetchAllCourses } from '../../redux/action/courseAction'
import { calculateAverageRating } from '../../util/mathUtils'

const subTitle = 'Featured Courses'
const title = 'Pick A Course To Get Started'

const Course = () => {
  const dispatch = useDispatch()
  const { courses } = useSelector((state) => state.course)

  useEffect(() => {
    fetchAllCourses(dispatch)
  }, [])

  return (
    <div className="course-section padding-tb section-bg">
      <div className="container">
        <div className="section-header text-center">
          <span className="subtitle">{subTitle}</span>
          <h2 className="title">{title}</h2>
        </div>
        <div className="section-wrapper">
          <div className="row g-4 justify-content-center row-cols-xl-3 row-cols-md-2 row-cols-1">
            {[...courses]
              .reverse()
              .splice(0, 3)
              .map((course, i) => (
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
                        <Link to={course.is_enrolled ? `/view-course/${course.id}` : `/course/detail/${course.id}`}>
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
        </div>
      </div>
    </div>
  )
}

export default Course
