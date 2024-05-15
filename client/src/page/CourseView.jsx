import React, { Fragment, useEffect, useRef, useState } from 'react'

import { Link, useParams } from 'react-router-dom'
import { Footer, Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import withBaseLogic from '../hoc/withBaseLogic'
import { fetchCourseDetail } from '../redux/action/courseAction'
import { timeAgo } from '../util/dateUtils'
import { showToastError } from '../util/toastAction'
import { Rating } from '../component/sidebar'

const CourseView = ({ user }) => {
  const params = useParams()
  const { courseId } = params
  const [icon, setIcon] = useState(false)
  const [courseDetail, setCourseDetail] = useState(null)
  const [videoUrl, setVideoUrl] = useState('')

  const videoRef = useRef(null)

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load()
      videoRef.current.pause()
    }
  }, [videoUrl])

  useEffect(() => {
    if (courseId) {
      function next(courseDetail) {
        setCourseDetail(courseDetail)

        if (courseDetail.videos && courseDetail.videos.length > 0) {
          setVideoUrl(courseDetail.videos[0].source)
        }
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
      <PageHeader title={courseDetail.name} curPage={courseDetail.name} />
      <div className="course-view-section padding-tb section-bg">
        <div className="container">
          <div className="row">
            <div className="col-12">
              <div className="course-view">
                <div className="row justify-content-center">
                  <div className="col-lg-8 col-12">
                    <div className="video-part mb-4 mb-lg-0">
                      <div className="vp-title mb-4">
                        <h3>
                          Enjoy your course! <span className="text-primary">{user.name}</span>{' '}
                        </h3>
                      </div>
                      <div className="vp-video mb-4">
                        <video muted={true} ref={videoRef} key={videoUrl} controls>
                          <source src={videoUrl} type="video/mp4" />
                        </video>
                      </div>
                      <div className={`content-wrapper ${icon ? 'open' : ''}`}>
                        <div className="content-icon d-lg-none" onClick={() => setIcon(!icon)}>
                          <i className="icofont-caret-down"></i>
                        </div>
                        <div className="vp-content mb-5">
                          <h4>Assignments</h4>
                          <div className="d-flex gap-3 mb-3">
                            <Link to={'/'}>
                              <img
                                style={{ width: '50px', height: '50px', objectFit: 'contain' }}
                                src="https://quizizz.com/media/resource/gs/quizizz-media/quizzes/7adc3a45-008a-4477-99f1-325fbb51b8ff"
                                alt=""
                                className="src"
                              />
                            </Link>
                            <Link to={'/'}>
                              <img
                                style={{ width: '50px', height: '50px', objectFit: 'contain' }}
                                src="https://cdn0.iconfinder.com/data/icons/file-types-65/512/150_pdf_file_type_file_format_file_extension_document-1024.png"
                                alt=""
                                className="src"
                              />
                            </Link>
                          </div>
                          <div className="mb-3">
                            <p className="text-primary f-5">New feature:</p>
                            <span className="text-secondary ">
                              This is the new AI for summarize your document shorter to easily reach the content of
                              them. But we encourage you to read the document carefully to complete your assignment.
                            </span>
                          </div>
                          <div className="mb-3">
                            <span className="text-secondary">Put the file you need to summarize</span>
                            <input type="file" />
                          </div>
                          <textarea rows={5} className="" placeholder="Summarized text"></textarea>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="col-lg-4 col-sm-8 col-12">
                    <div className="video-list-area">
                      <div className="video-head"></div>
                      <div className="video-body">
                        <div className="course-select-area border-radius-12">
                          <div className="csa-title mb-4">
                            <h5>Course Videos</h5>
                          </div>
                          <div className="csa-body">
                            <ul className="lab-ul">
                              <li>
                                <div id="accordion">
                                  {courseDetail.videos.map((video, i) => (
                                    <div key={i} className="card active bg-ash mb-1">
                                      <div className="card-header bg-transparent border-bottom-0" id="acc-list-1">
                                        <h5 className="mb-0">
                                          <button
                                            className={`${
                                              videoUrl === video.source && `text-primary`
                                            } w-100 border-0 bg-transparent outline-none text-left`}
                                            data-bs-toggle="collapse"
                                            data-bs-target={`#acc-${i + 1}`}
                                            aria-expanded="true"
                                            aria-controls={`acc-${i + 1}`}
                                          >
                                            Lesson {i + 1}: {video.title}
                                          </button>
                                        </h5>
                                      </div>
                                      <div
                                        onClick={() => setVideoUrl(video.source)}
                                        id={`acc-${i + 1}`}
                                        className="collapse show"
                                        aria-labelledby={`acc-list-${i + 1}`}
                                        data-bs-parent="#accordion"
                                      >
                                        <div className="card-body py-0">
                                          <div
                                            className={`${
                                              videoUrl === video.source && `text-primary`
                                            } course-lists d-flex flex-wrap justify-content-between`}
                                          >
                                            <div className="csa-left">
                                              {/* <i className="icofont-checked complite"></i> */}
                                              <i className="icofont-checked"></i>
                                              <h6 className={`${videoUrl === video.source && `text-primary`}`}>
                                                1.1 Introduction
                                              </h6>
                                              <p className={`${videoUrl === video.source && `text-primary`}`}>
                                                <i className="icofont-play-alt-2"></i>
                                                6:00 Min
                                              </p>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  ))}
                                </div>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="overview-announce-section padding-tb">
        <div className="container">
          <div className="row">
            <div className="col-12">
              <div className="course-view-bottom">
                <ul className="nav nav-tabs" id="myTab" role="tablist">
                  <li className="nav-item">
                    <a
                      className="nav-link active"
                      id="overview-tab"
                      data-bs-toggle="tab"
                      href="#overview"
                      role="tab"
                      aria-controls="overview"
                      aria-selected="true"
                    >
                      Overview
                    </a>
                  </li>
                  <li className="nav-item">
                    <a
                      className="nav-link"
                      id="announcement-tab"
                      data-bs-toggle="tab"
                      href="#announcement"
                      role="tab"
                      aria-controls="announcement"
                      aria-selected="false"
                    >
                      Announcement
                    </a>
                  </li>
                </ul>
                <div className="tab-content" id="myTabContent">
                  <div
                    className="tab-pane fade show active"
                    id="overview"
                    role="tabpanel"
                    aria-labelledby="overview-tab"
                  >
                    <div className="overview-area">
                      <div className="overview-head mb-4">
                        <h6 className="mb-0">About this course</h6>
                        <p>{courseDetail.preDescription}</p>
                      </div>
                      <div className="overview-body">
                        <ul className="lab-ul">
                          <li className="d-flex flex-wrap">
                            <div className="overview-left">
                              <p className="mb-0">By the numbers</p>
                            </div>
                            <div className="overview-right">
                              <div className="or-items d-flex flex-wrap">
                                <div className="or-left mr-3">Skill level</div>
                                <div className="or-right">Beginner Leve</div>
                              </div>
                              <div className="or-items d-flex flex-wrap">
                                <div className="or-left mr-3">Students</div>
                                <div className="or-right">17118</div>
                              </div>
                              <div className="or-items d-flex flex-wrap">
                                <div className="or-left mr-3">Languages</div>
                                <div className="or-right">English</div>
                              </div>
                              <div className="or-items d-flex flex-wrap">
                                <div className="or-left mr-3">Captions</div>
                                <div className="or-right">Yes</div>
                              </div>
                              <div className="or-items d-flex flex-wrap">
                                <div className="or-left mr-3">Lectures</div>
                                <div className="or-right">31</div>
                              </div>
                              <div className="or-items d-flex flex-wrap">
                                <div className="or-left mr-3">Video</div>
                                <div className="or-right">1 total hour</div>
                              </div>
                            </div>
                          </li>
                          <li className="d-flex flex-wrap">
                            <div className="overview-left">
                              <p className="mb-0">Features</p>
                            </div>
                            <div className="overview-right">
                              <p className="catagory mb-0">Standard Website Design</p>
                            </div>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div className="tab-pane" id="announcement" role="tabpanel" aria-labelledby="announcement-tab">
                    <div className="announcement-area">
                      <div className="announcement-lists">
                        <ul className="lab-ul">
                          <li>
                            <div className="administer-post d-flex flex-wrap">
                              <div className="ap-top mb-2">
                                <div className="ap-items d-flex flex-wrap">
                                  <div className="ap-thumb">
                                    <img src={courseDetail.instructor.avatar} alt="administer" />
                                  </div>
                                  <div className="ap-content">
                                    <h5>{courseDetail.instructor.name}</h5>
                                    <p>{courseDetail.instructor.email}</p>
                                  </div>
                                </div>
                              </div>
                              <div className="ap-bottom">
                                <h4>The best way to study animation is practice!</h4>
                                <p>
                                  I want to clarify some details right away: This course was created for the training
                                  unit of the General-Animation studio. Every new candidate who wants to become a member
                                  of our studio, we send to this course so that he can quickly and efficiently become
                                  familiar with our methods of work.
                                </p>
                                <p>
                                  Many members of our community live in developing countries and have access only to the
                                  simplest computers. Taking into account the needs of such students, during training we
                                  use the Adobe After Effects of 7.0 version, which does not require high-performance
                                  hardware, but at the same time allows to master the basic principles of working with
                                  the program
                                </p>
                              </div>
                            </div>
                          </li>
                          <li>
                            <div className="student-reviwe d-flex flex-wrap">
                              <div className="student-thumb">
                                <img src={user.avatar} alt="annaunce" />
                              </div>
                              <div className="student-content">
                                <form action="" className="d-flex" method="post">
                                  <textarea
                                    name="meassage"
                                    id="meassage"
                                    cols="30"
                                    rows="1"
                                    placeholder="Enter Your Comment"
                                  ></textarea>
                                  <button className="lab-btn text-white">Submit</button>
                                </form>
                              </div>
                            </div>
                          </li>
                          {courseDetail?.reviews
                            .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
                            .map((review, i) => (
                              <li key={i}>
                                <div className="student-reviwe d-flex flex-wrap">
                                  <div className="student-thumb">
                                    <img src={review.user.avatar} alt="review" />
                                  </div>
                                  <div className="student-content">
                                    <h6>
                                      {review.user.name} <span>{timeAgo(review.createdAt)}</span>
                                    </h6>
                                    <Rating value={review.rating} />
                                    <p>{review.comment}</p>
                                  </div>
                                </div>
                              </li>
                            ))}
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
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

export default withBaseLogic(CourseView)
