import { Fragment, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { Footer, Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import { Review } from '../component/sidebar'
import Author from '../component/sidebar/author'
import CourseSideCetagory from '../component/sidebar/course-cetagory'
import Respond from '../component/sidebar/respond'
import withBaseLogic from '../hoc/withBaseLogic'
import { fetchCourseDetail } from '../redux/action/courseAction'
import { showToastError } from '../util/toastAction'

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

const CourseSingle = () => {
  const [courseDetail, setCourseDetail] = useState(null)
  const params = useParams()
  const { courseId } = params
  const [isEnrolled, setEnrolled] = useState(false)

  useEffect(() => {
    if (courseId) {
      function next(courseDetail, isEnrolled) {
        setEnrolled(isEnrolled)
        setCourseDetail(courseDetail)
      }
      function errorHandle(message) {
        showToastError(message)
      }
      fetchCourseDetail(courseId, next, errorHandle)
    }
  }, [courseId])

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
                              <li className=" d-flex flex-wrap justify-content-between">
                                <div className="video-item-title">{`${i + 1}. ${video.title}`}</div>
                              </li>
                            ))}
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <Author />
                <Review />
                <Respond />
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
                    {isEnrolled ? (
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
