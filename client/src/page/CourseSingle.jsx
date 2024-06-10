import { Fragment, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { Footer, Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import { Rating } from '../component/sidebar'
import CourseSideCetagory from '../component/sidebar/course-cetagory'
import withBaseLogic from '../hoc/withBaseLogic'
import { fetchCourseDetail, postReview } from '../redux/action/courseAction'
import { showToastError, showToastSuccess } from '../util/toastAction'

const price = '89'
const excenge = 'Limited time offer'
const shareTitle = 'Share This Course:'
const btnText = 'Enrolled Now'

const csdcList = [
  {
    iconName: 'icofont-ui-alarm',
    leftText: 'Course level',
    rightText: 'Beginner'
  },
  {
    iconName: 'icofont-book-alt',
    leftText: 'Course Duration',
    rightText: '10 week'
  },
  {
    iconName: 'icofont-signal',
    leftText: 'Online Class',
    rightText: '08'
  },
  {
    iconName: 'icofont-video-alt',
    leftText: 'Lessions',
    rightText: '18x'
  },
  {
    iconName: 'icofont-abacus-alt',
    leftText: 'Quizzes',
    rightText: '0'
  },
  {
    iconName: 'icofont-hour-glass',
    leftText: 'Pass parcentages',
    rightText: '80'
  },
  {
    iconName: 'icofont-certificate',
    leftText: 'Certificate',
    rightText: 'Yes'
  },
  {
    iconName: 'icofont-globe',
    leftText: 'Language',
    rightText: 'English'
  }
]

const socialList = [
  {
    siteLink: '#',
    iconName: 'icofont-twitter',
    className: 'twitter'
  },
  {
    siteLink: '#',
    iconName: 'icofont-vimeo',
    className: 'vimeo'
  },
  {
    siteLink: '#',
    iconName: 'icofont-rss',
    className: 'rss'
  }
]

const CourseSingle = ({ user }) => {
  const [courseDetail, setCourseDetail] = useState(null)
  const [loading, setLoading] = useState(false)
  const [rating, setRating] = useState(0)
  const [showAll, setShowAll] = useState(false)
  const [visibleReviews, setVisibleReviews] = useState(3)
  const [comment, setComment] = useState('')
  const params = useParams()
  const { courseId } = params
  const loadMoreReviews = () => {
    if (visibleReviews + 3 >= courseDetail.reviews.length) {
      setShowAll(true)
    }
    setVisibleReviews((prevVisibleReviews) => prevVisibleReviews + 3)
  }

  const showLessReviews = () => {
    setVisibleReviews(3)
    setShowAll(false)
  }

  useEffect(() => {
    if (courseId) {
      function next(courseDetail) {
        setCourseDetail(courseDetail)
      }
      function errorHandle(message) {
        showToastError(message)
      }
      fetchCourseDetail(courseId, next, errorHandle)
    }
  }, [courseId])

  const submitReview = () => {
    if (loading) return
    const form = { rating, comment, course_id: courseId }
    setLoading(true)
    function next(newReview) {
      setCourseDetail((prevCourseDetail) => {
        // Create a new array with the existing reviews and the new review
        const updatedReviews = [...prevCourseDetail.reviews, newReview]
        // Return a new object for the course detail with the updated reviews array
        return {
          ...prevCourseDetail,
          reviews: updatedReviews
        }
      })
      clearForm()
      showToastSuccess('Create review successfully!')
    }
    function errorHandle(message) {
      showToastError(message)
    }
    postReview(form, next, errorHandle)
    setLoading(false)
  }

  function clearForm() {
    setComment('')
    setRating(0)
  }

  return courseDetail ? (
    <Fragment>
      <Header />
      <PageHeader curPage={courseDetail.name} />
      <div className="course-single-section padding-tb section-bg">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-lg-8">
              <div className="main-part">
                <div dangerouslySetInnerHTML={{ __html: courseDetail.content }} />

                <div className="course-video">
                  <div className="course-video-content">
                    <div className="accordion" id="accordionExample">
                      <div className="accordion-item">
                        <div className="accordion-header" id="accordion01">
                          <button
                            className="d-flex flex-wrap justify-content-between"
                            data-bs-toggle="collapse"
                            data-bs-target="#videolist1"
                            aria-expanded="true"
                            aria-controls="videolist1"
                          >
                            <span>Course content</span> <span>{courseDetail.videos.length} lessons</span>{' '}
                          </button>
                        </div>
                        <div
                          id="videolist1"
                          className="accordion-collapse collapse show"
                          aria-labelledby="accordion01"
                          data-bs-parent="#accordionExample"
                        >
                          <ul className="lab-ul video-item-list">
                            {courseDetail.videos.map((video, i) => (
                              <li key={i} className=" d-flex flex-wrap justify-content-between">
                                <div className="video-item-title">{`${i + 1}. ${video.title}`}</div>
                              </li>
                            ))}
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="authors">
                  <div className="author-thumb">
                    <img src={courseDetail.instructor.avatar} alt="rajibraj91" />
                  </div>
                  <div className="author-content">
                    <h5>{courseDetail.instructor.name}</h5>
                    <span>Teacher</span>
                    <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Nemo, cupiditate!</p>
                    <ul className="lab-ul social-icons">
                      {socialList.map((val, i) => (
                        <li key={i}>
                          <a href={val.link} className={val.className}>
                            <i className={val.iconName}></i>
                          </a>
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>
                <div className="comments">
                  <h4 className="title-border">{courseDetail.reviews.length} reviews</h4>
                  <ul className="comment-list">
                    {courseDetail.reviews.slice(0, visibleReviews).map((r, i) => (
                      <li className="comment" key={i}>
                        <div className="com-thumb">
                          <img src={r.user.avatar} alt={r.user.name} />
                        </div>
                        <div className="com-content">
                          <div className="com-title">
                            <div className="com-title-meta">
                              <h6>{r.user.name}</h6>
                              <span> {r.createdAt.split('T')[0]} </span>
                            </div>
                            <Rating value={r.rating} />
                          </div>
                          <p>{r.comment}</p>
                        </div>
                      </li>
                    ))}
                  </ul>
                  {visibleReviews < courseDetail.reviews.length && (
                    <div className="w-100 text-center">
                      <button onClick={loadMoreReviews} className="mb-3 lab-btn text-white">
                        Load more
                      </button>
                    </div>
                  )}
                  {visibleReviews > 3 && (
                    <div className="w-100 text-center">
                      <button onClick={showLessReviews} className="mb-3 lab-btn text-white">
                        Show less
                      </button>
                    </div>
                  )}
                </div>
                {courseDetail.is_enrolled && !courseDetail.reviews.some((r) => r.user.id !== user.id) && (
                  <div id="respond" className="comment-respond mb-lg-0">
                    <h4 className="title-border">Leave a review</h4>
                    <div className="add-comment">
                      <div id="commentform" className="comment-form">
                        <p>{user.name}</p>
                        <div className="mb-2">
                          <Rating editable value={rating} onChange={setRating} />
                        </div>
                        <textarea
                          value={comment}
                          onChange={(e) => setComment(e.target.value)}
                          rows="7"
                          type="text"
                          name="message"
                          placeholder="Your Comment"
                        ></textarea>
                        <button onClick={() => submitReview()} type="submit" className="lab-btn">
                          <span>Submit</span>
                        </button>
                      </div>
                    </div>
                  </div>
                )}
              </div>
            </div>
            <div className="col-lg-4">
              <div className="sidebar-part">
                <div className="course-side-detail">
                  <div className="csd-title">
                    <div className="csdt-left">
                      <h4 className="mb-0">
                        <sup>$</sup>
                        {price}
                      </h4>
                    </div>
                    <div className="csdt-right">
                      <p className="mb-0">
                        <i className="icofont-clock-time"></i>
                        {excenge}
                      </p>
                    </div>
                  </div>
                  <div className="csd-content">
                    <div className="csdc-lists">
                      <ul className="lab-ul">
                        {csdcList.map((val, i) => (
                          <li key={i}>
                            <div className="csdc-left">
                              <i className={val.iconName}></i>
                              {val.leftText}
                            </div>
                            <div className="csdc-right">{val.rightText}</div>
                          </li>
                        ))}
                      </ul>
                    </div>
                    <div className="sidebar-social">
                      <div className="ss-title">
                        <h6>{shareTitle}</h6>
                      </div>
                      <div className="ss-content">
                        <ul className="lab-ul">
                          {socialList.map((val, i) => (
                            <li key={i}>
                              <a href={val.siteLink} className={val.className}>
                                <i className={val.iconName}></i>
                              </a>
                            </li>
                          ))}
                        </ul>
                      </div>
                    </div>
                    {courseDetail.is_enrolled ? (
                      <Link to={`/view-course/${courseId}`} className="course-enroll">
                        <button className="lab-btn bg-dark">
                          <span>View Course</span>
                        </button>
                      </Link>
                    ) : (
                      <div className="course-enroll">
                        <Link to={`/enroll/${courseId}`} className="lab-btn">
                          <span>{btnText}</span>
                        </Link>
                      </div>
                    )}
                  </div>
                </div>
                <CourseSideCetagory />
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(CourseSingle)
