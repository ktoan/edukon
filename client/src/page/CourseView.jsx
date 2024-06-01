import React, { Fragment, useEffect, useRef, useState } from 'react'
import axios from 'axios'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { Footer, Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import LoadingInner from '../component/section/LoadingInner.'
import withBaseLogic from '../hoc/withBaseLogic'
import { fetchCourseDetail } from '../redux/action/courseAction'
import { showToastError } from '../util/toastAction'

const CourseView = ({ user }) => {
  const [file, setFile] = useState(null)
  const [summary, setSummary] = useState('')
  const params = useParams()
  const { courseId } = params
  const [icon, setIcon] = useState(false)
  const [courseDetail, setCourseDetail] = useState(null)
  const [loading, setLoading] = useState(false)
  const [videoUrl, setVideoUrl] = useState('')
  const [videoDurations, setVideoDurations] = useState({})

  const videoRef = useRef(null)
  const navigate = useNavigate()

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load()
      videoRef.current.pause()
    }
  }, [videoUrl])

  useEffect(() => {
    if (courseDetail != null) {
      console.log(courseDetail)
    }
  }, [courseDetail])

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

  const onFileChange = (event) => {
    setFile(event.target.files[0])
  }

  const onFileUpload = async () => {
    if (!loading) {
      setLoading(true)
      const formData = new FormData()
      formData.append('file', file)

      await axios
        .post('http://127.0.0.1:5000/summarize', formData)
        .then((response) => {
          setSummary(response.data.summary)
        })
        .catch((error) => {
          setSummary('')
          console.error('There was an error!', error)
        })
      setLoading(false)
    }
  }

  const handleLoadedMetadata = (videoId) => {
    const videoElement = document.getElementById(videoId)
    if (videoElement) {
      setVideoDurations((prevDurations) => ({
        ...prevDurations,
        [videoId]: videoElement.duration
      }))
    }
  }

  const formatDuration = (duration) => {
    const minutes = Math.floor(duration / 60)
    const seconds = Math.floor(duration % 60)
    return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`
  }

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
                            <input type="file" className="mb-2" onChange={onFileChange} />
                            <button disabled={loading} onClick={onFileUpload} className="lab-btn text-white fs-6">
                              {loading ? `Generating...` : `Generate`}
                            </button>
                          </div>
                          <textarea
                            readOnly
                            value={summary}
                            rows={5}
                            className=""
                            placeholder="Summarized text"
                          ></textarea>
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
                                    <div key={i} className="card bg-ash mb-1">
                                      <div onClick={() => setVideoUrl(video.source)} className="collapse show">
                                        <div className="card-body py-0">
                                          <div className="course-lists d-flex flex-wrap justify-content-between">
                                            <div className="csa-left" style={{ width: '100%', overflow: 'hidden' }}>
                                              <i
                                                className={`${
                                                  video.tracked ? `icofont-check-circled text-success` : `icofont-lock`
                                                }`}
                                              ></i>
                                              <h6
                                                style={{
                                                  textOverflow: 'ellipsis',
                                                  maxWidth: '100%',
                                                  whiteSpace: 'nowrap',
                                                  overflow: 'hidden'
                                                }}
                                              >
                                                Lesson {i + 1}: {video.title}
                                              </h6>
                                              <p>
                                                <i className="icofont-play-alt-2"></i>
                                                {videoDurations[`video-${i}`]
                                                  ? formatDuration(videoDurations[`video-${i}`])
                                                  : 'Loading...'}
                                              </p>
                                              <video
                                                id={`video-${i}`}
                                                onLoadedMetadata={() => handleLoadedMetadata(`video-${i}`)}
                                                style={{ display: 'none' }}
                                              >
                                                <source src={video.source} type="video/mp4" />
                                              </video>
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
      <Footer />
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(CourseView)
