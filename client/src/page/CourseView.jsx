import axios from 'axios'
import React, { Fragment, useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Footer, Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import withBaseLogic from '../hoc/withBaseLogic'
import {
  createCertificate,
  createSubmission,
  createTrackingProgress,
  fetchCourseDetail
} from '../redux/action/courseAction'
import { showToastError, showToastSuccess } from '../util/toastAction'

const CourseView = ({ user }) => {
  const [file, setFile] = useState(null)
  const [summary, setSummary] = useState('')
  const params = useParams()
  const { courseId } = params
  const [icon, setIcon] = useState(false)
  const [courseDetail, setCourseDetail] = useState(null)
  const [loading, setLoading] = useState(false)
  const [loadingCertificate, setLoadingCertificicate] = useState(false)
  const [video, setVideo] = useState(null)
  const [videoAssignment, setVideoAssignment] = useState(null)
  const [videoDurations, setVideoDurations] = useState({})
  const [renderingAI, setRenderingAI] = useState('ABSTRACT')
  const [submission, setSubmission] = useState(null)

  const videoRef = useRef(null)

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load()
      videoRef.current.pause()
    }
  }, [video])

  useEffect(() => {
    if (courseId) {
      function next(courseDetail) {
        setCourseDetail(courseDetail)

        if (courseDetail.videos && courseDetail.videos.length > 0) {
          setVideo(courseDetail.videos[0])
          setVideoAssignment(courseDetail.videos[0].assignment ? courseDetail.videos[0].assignment : null)
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

  const handleVideoEnded = (videoId) => {
    let currentVideo = courseDetail.videos.find((v, i) => v.id === videoId)
    let nextVideo = courseDetail.videos.find((v, i) => v.id === videoId + 1 && i < courseDetail.videos.length)
    if (nextVideo !== undefined) {
      setVideo(nextVideo)
    }
    if (!currentVideo.tracked) {
      createTrackingProgress(
        videoId,
        (isTracked) => {
          const updatedVideos = courseDetail.videos.map((video) => {
            if (video.id === videoId) {
              return { ...video, tracked: isTracked }
            }
            return video
          })
          setCourseDetail((courseDetail) => ({
            ...courseDetail,
            videos: updatedVideos
          }))
        },
        (message) => {
          console.log(message)
        }
      )
    }
  }

  const handleDownloadCertificate = () => {
    setLoadingCertificicate(true)
    function next(certificate) {
      if (certificate) {
        const link = document.createElement('a')
        link.href = certificate.source
        link.setAttribute('download', `${user.name}_${courseDetail.name}.pdf`)
        link.setAttribute('target', '_blank')
        console.log(link)
        link.click()
        document.body.removeChild(link)
      }
      setLoadingCertificicate(false)
    }
    function errorHandle(message) {
      showToastError(message)
    }
    createCertificate(courseDetail.id, next, errorHandle)
  }

  function submitSubmission() {
    if (!submission) {
      showToastError('Please upload your submission')
    } else {
      const submitForm = new FormData()
      submitForm.append('file', submission)
      submitForm.append('assignmentId', videoAssignment.id)
      function next() {
        setCourseDetail((courseDetail) => ({ ...courseDetail, sourceSubmissionL: submission.source }))
        showToastSuccess('Your submission has been successfully uploaded!')
      }
      function errorHandle(message) {
        showToastError(message)
      }
      createSubmission(submitForm, next, errorHandle)
    }
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
                        <h3 className="text-primary">{video.title}</h3>
                      </div>
                      {!courseDetail.videos.find((v) => v.tracked === false) && !courseDetail.has_certificate && (
                        <div className="vp-title mb-4">
                          <button
                            disabled={loadingCertificate}
                            onClick={() => handleDownloadCertificate()}
                            className="rounded text-primary mb-0"
                          >
                            {loadingCertificate ? 'Getting certificate' : 'Get certificate'}
                          </button>
                        </div>
                      )}
                      {courseDetail.sourceCertificate && courseDetail.has_certificate && (
                        <a target="_blank" href={courseDetail.sourceCertificate} className="vp-title mb-4">
                          <button className="rounded text-primary mb-0">See my certificate</button>
                        </a>
                      )}
                      <div className="vp-video mb-4">
                        <video
                          muted={true}
                          ref={videoRef}
                          key={video.source}
                          controls
                          onEnded={() => handleVideoEnded(video.id)}
                        >
                          <source src={video.source} type="video/mp4" />
                        </video>
                      </div>
                      <div className={`content-wrapper ${icon ? 'open' : ''}`}>
                        <div className="content-icon d-lg-none" onClick={() => setIcon(!icon)}>
                          <i className="icofont-caret-down"></i>
                        </div>
                        <div className="vp-content mb-5">
                          {videoAssignment && (
                            <div className="card mb-3">
                              <div className="card-header">
                                <p className="text-primary fw-normal f-5">{videoAssignment.title}:</p>
                                <span className="text-secondary ">{videoAssignment.requirement}</span>
                              </div>
                              <div className="card-body">
                                <a
                                  title="References"
                                  className="d-flex align-items-center gap-3"
                                  target="_blank"
                                  rel="noopener"
                                  href={videoAssignment.source}
                                >
                                  <img
                                    style={{ width: '32px', height: '32px', objectFit: 'contain' }}
                                    src="../assets/images/google-docs.png"
                                    alt=""
                                    className="src"
                                  />
                                  <span>{videoAssignment.title} (Click to see details)</span>
                                </a>
                              </div>
                            </div>
                          )}
                          <div className="card mb-3">
                            <div className="card-header">
                              <p className="text-primary fw-normal f-5 mb-2">New feature:</p>
                              <p className="text-secondary mb-2">
                                This is the new AI for summarize your document shorter to easily reach the content of
                                them. But we encourage you to read the document carefully to complete your assignment.
                              </p>
                            </div>
                            <div className="card-body">
                              <div className="d-flex align-items-center gap-2 mb-2">
                                <p className="mb-0">Select type of render AI:</p>
                                <p
                                  onClick={() => setRenderingAI('ABSTRACT')}
                                  style={{ cursor: 'pointer' }}
                                  className={`mb-0 ${renderingAI === 'ABSTRACT' ? `text-primary` : `text-secondary`}`}
                                >
                                  Abstract AI
                                </p>
                                <p className="mb-0 text-secondary">|</p>
                                <p
                                  onClick={() => setRenderingAI('EXTRACT')}
                                  style={{ cursor: 'pointer' }}
                                  className={`mb-0 ${renderingAI === 'EXTRACT' ? `text-primary` : `text-secondary`}`}
                                >
                                  Extract AI
                                </p>
                              </div>
                              <hr />
                              <p>Compare 2 types of summarization text AI:</p>
                              <table className="table">
                                <thead>
                                  <tr>
                                    <th className="text-center border">Aspects</th>
                                    <th
                                      className={`text-center border ${renderingAI === 'ABSTRACT' && `text-primary`}`}
                                    >
                                      Abstract
                                    </th>
                                    <th className={`text-center border ${renderingAI === 'EXTRACT' && `text-primary`}`}>
                                      Extract
                                    </th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <tr>
                                    <td className="border">Definition</td>
                                    <td className={`border ${renderingAI === 'ABSTRACT' && `text-primary`}`}>
                                      Selects key sentences from the original text{' '}
                                    </td>
                                    <td className={`border ${renderingAI === 'EXTRACT' && `text-primary`}`}>
                                      Generates new sentences that convey main ideas
                                    </td>
                                  </tr>
                                  <tr>
                                    <td class="border">Approach</td>
                                    <td className={`border ${renderingAI === 'ABSTRACT' && `text-primary`}`}>
                                      Ranks and selects sentences based on criteria
                                    </td>
                                    <td className={`border ${renderingAI === 'EXTRACT' && `text-primary`}`}>
                                      Uses NLP to understand and generate summaries
                                    </td>
                                  </tr>
                                  <tr>
                                    <td class="border">Pros</td>
                                    <td className={`border ${renderingAI === 'ABSTRACT' && `text-primary`}`}>
                                      Maintains original wording, simpler to implement
                                    </td>
                                    <td className={`border ${renderingAI === 'EXTRACT' && `text-primary`}`}>
                                      More coherent and fluent, captures nuanced meaning
                                    </td>
                                  </tr>
                                  <tr>
                                    <td class="border">Cons</td>
                                    <td className={`border ${renderingAI === 'ABSTRACT' && `text-primary`}`}>
                                      May lack coherence, can miss context
                                    </td>
                                    <td className={`border ${renderingAI === 'EXTRACT' && `text-primary`}`}>
                                      More complex, higher risk of errors
                                    </td>
                                  </tr>
                                  <tr>
                                    <td class="border">Use Cases</td>
                                    <td className={`border ${renderingAI === 'ABSTRACT' && `text-primary`}`}>
                                      Quick, factual summaries, legal documents
                                    </td>
                                    <td className={`border ${renderingAI === 'EXTRACT' && `text-primary`}`}>
                                      Creative writing, articles, and complex texts
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                              <hr />
                              <p className="text-secondary mb-2">Put the file you need to summarize</p>
                              <input type="file" className="mb-2" onChange={onFileChange} />
                              <button
                                disabled={loading}
                                onClick={onFileUpload}
                                className="lab-btn text-white fs-6 mb-2"
                              >
                                {loading ? `Generating...` : `Generate`}
                              </button>
                              <textarea
                                readOnly
                                value={summary}
                                rows={5}
                                className=""
                                placeholder="Summarized text"
                              ></textarea>
                            </div>
                          </div>
                          {videoAssignment && !videoAssignment.submissions.find((s) => s.student.id === user.id) && (
                            <div className="card">
                              <div className="card-header">
                                <span className="text-primary fw-normal f-5">Submit your assignment</span>
                              </div>
                              <div className="card-body">
                                <span className="text-secondary mb-2">
                                  Your submission is received only once, please take care before submit them.
                                </span>
                                <input
                                  type="file"
                                  onChange={(e) => setSubmission(e.target.files[0])}
                                  className="mb-2"
                                />
                                <button onClick={() => submitSubmission()} className="lab-btn text-white fs-6 mb-2">
                                  Submit
                                </button>
                              </div>
                            </div>
                          )}
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
                                      <div
                                        onClick={() => {
                                          setVideo(video)
                                          setVideoAssignment(video.assignment)
                                        }}
                                        className="collapse show"
                                      >
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
