import React, { Fragment, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import withBaseLogic from '../hoc/withBaseLogic'
import { getEnrolledCourse } from '../redux/action/enrollAction'
import { logoutUser } from '../redux/action/authAction'
import { changeUserAvatar, updateUserInformation } from '../redux/action/userAction'
import { showToastError, showToastSuccess } from '../util/toastAction'
import { Modal } from 'react-bootstrap'
import { getUserCertificates } from '../redux/action/courseAction'

const Profile = ({ dispatch, user, requiredLogin }) => {
  const [avatarImage, setAvatarImage] = useState(null)
  const [changeAvatarModalShow, setChangeAvatarModalShow] = useState(false)
  const [changePasswordModalShow, setChangePasswordModalShow] = useState(false)
  const [enrolledCourse, setEnrolledCourse] = useState([])
  const [certificates, setCertificates] = useState([])
  const [informationForm, setInformationForm] = useState(
    user
      ? {
          name: user.name,
          phone: user.phone,
          gender: user.gender
        }
      : { name: '', phone: '', gender: '' }
  )

  function onChangeAvatarPicker(e) {
    let file = e.target.files[0]
    setAvatarImage(file)
  }

  function onChangeFormInput(e) {
    setInformationForm({ ...informationForm, [e.target.name]: e.target.value })
  }

  function onSubmitInformationForm(e) {
    e.preventDefault()
    function next() {
      showToastSuccess('Update information successfully!')
    }
    function errorHandle(message) {
      showToastError(message)
    }
    console.log(informationForm)
    if (informationForm.name === '' || !informationForm.gender === '' || informationForm.phone === '') {
      showToastError('Please fill out the information form!')
    } else {
      updateUserInformation(dispatch, informationForm, next, errorHandle)
    }
  }

  useEffect(() => {
    if (!user) requiredLogin()
  }, [user, requiredLogin])

  useEffect(() => {
    function next(courses) {
      setEnrolledCourse(courses)
    }
    function errorHandle(message) {
      console.log(message)
    }
    getEnrolledCourse(next, errorHandle)
  }, [])

  useEffect(() => {
    function next(c) {
      setCertificates(c)
    }
    function errorHandle(message) {
      console.log(message)
    }
    getUserCertificates(next, errorHandle)
  }, [])

  function onSubmitChangeAvatar() {
    let permitSubmit = true
    if (!avatarImage) {
      showToastError('New avatar is required to update!')
      permitSubmit = false
    }
    if (permitSubmit) {
      const submitForm = new FormData()
      submitForm.append('file', avatarImage)
      function next() {
        setChangeAvatarModalShow(false)
        showToastSuccess('Update avatar successfully!')
      }
      function errorHandle(message) {
        showToastError(message)
      }
      changeUserAvatar(dispatch, submitForm, next, errorHandle)
    }
  }

  return user ? (
    <Fragment>
      {/* Change avatar modal */}
      <Modal close show={changeAvatarModalShow} onHide={() => setChangeAvatarModalShow(false)}>
        <Modal.Header>
          <p className="text-primary fw-bold mb-0">Change avatar</p>
        </Modal.Header>
        <Modal.Body>
          <input type="file" onChange={onChangeAvatarPicker} />
          <div className="text-center">
            <img
              className="mt-2 rounded"
              style={{ objectFit: 'cover' }}
              src={avatarImage ? URL.createObjectURL(avatarImage) : user.avatar}
              width={'200px'}
              height={'200px'}
            />
          </div>
        </Modal.Body>
        <Modal.Footer>
          <button onClick={() => onSubmitChangeAvatar()} className="lab-btn text-white">
            Submit
          </button>
        </Modal.Footer>
      </Modal>
      {/* Change password modal */}
      <Modal close show={changePasswordModalShow} onHide={() => setChangePasswordModalShow(false)}>
        <Modal.Header>
          <p className="text-primary fw-bold mb-0">Change password</p>
        </Modal.Header>
        <Modal.Body>
          <input type="password" placeholder="Current Password" className="mb-2" />
          <input type="password" placeholder="New Password" className="mb-2" />
          <input type="password" placeholder="Confirm New Password" />
        </Modal.Body>
        <Modal.Footer>
          <button className="lab-btn text-white">Submit</button>
        </Modal.Footer>
      </Modal>
      <Header />
      <PageHeader title={user.name} curPage={'Profile'} />
      <ul className="nav nav-tabs" id="myTab" role="tablist">
        <li className="nav-item">
          <a
            className="nav-link active text-dark"
            id="general-tab"
            data-bs-toggle="tab"
            href="#general"
            role="tab"
            aria-controls="overview"
            aria-selected="true"
          >
            General
          </a>
        </li>
        <li className="nav-item">
          <a
            className="nav-link text-dark"
            id="course-tab"
            data-bs-toggle="tab"
            href="#course"
            role="tab"
            aria-controls="overview"
            aria-selected="true"
          >
            Manage Course
          </a>
        </li>
        <li className="nav-item">
          <a
            className="nav-link text-dark"
            id="certificate-tab"
            data-bs-toggle="tab"
            href="#certificate"
            role="tab"
            aria-controls="overview"
            aria-selected="true"
          >
            Manage Certificate
          </a>
        </li>
      </ul>
      <div className="tab-content" id="myTabContent">
        <div className="tab-pane fade show active" id="general" role="tabpanel" aria-labelledby="general-tab">
          <div className="p-3">
            <div className="d-flex gap-2">
              <div className="col-6">
                <h3>Information</h3>
                <form onSubmit={(e) => onSubmitInformationForm(e)} className="mb-2">
                  <div className="nf-list mb-2">
                    <input type="email" name="email" placeholder="Enter Your Email" value={user.email} disabled />
                  </div>
                  <div className="nf-list mb-2">
                    <input
                      type="text"
                      name="name"
                      onChange={(e) => onChangeFormInput(e)}
                      placeholder="Enter Your Name"
                      value={informationForm.name}
                    />
                  </div>
                  <div className="nf-list mb-2">
                    <input
                      type="text"
                      name="phone"
                      onChange={(e) => onChangeFormInput(e)}
                      placeholder="Enter Your Phone"
                      value={informationForm.phone}
                    />
                  </div>
                  <div className="nf-list mb-2">
                    <select value={informationForm.gender} onChange={(e) => onChangeFormInput(e)} name="gender">
                      <option>Gender</option>
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                      <option value="DISCLOSED">Disclosed</option>
                    </select>
                  </div>
                  <button type="submit" className="lab-btn text-white">
                    Update information
                  </button>
                </form>
              </div>
              <div className="col-6">
                <div className="card">
                  <div className="card-header">Others</div>
                  <div className="card-body">
                    <button onClick={() => setChangeAvatarModalShow(true)} className="lab-btn text-white mb-2">
                      Change avatar
                    </button>
                    <br></br>
                    <button
                      onClick={() => setChangePasswordModalShow(true)}
                      className="lab-btn bg-warning text-white mb-2"
                    >
                      Update password
                    </button>
                    <br></br>
                    <button onClick={() => logoutUser(dispatch)} className="lab-btn bg-danger text-white">
                      Sign out
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="tab-pane" id="course" role="tabpanel" aria-labelledby="course-tab">
          <div className="shop-cart padding-tb">
            <div className="container">
              <div className="section-wrapper">
                <div className="cart-top">
                  <table>
                    <thead>
                      <tr>
                        <th>No</th>
                        <th>Course</th>
                        <th>Category</th>
                        <th>No Lessons</th>
                        <th>Instructor</th>
                        <th>Price</th>
                      </tr>
                    </thead>
                    <tbody>
                      {enrolledCourse.map((c, i) => (
                        <tr key={i}>
                          <td>{c.id}</td>
                          <td>
                            <Link to={`/view-course/${c.id}`}>
                              <p className="text-primary mb-0 fw-bold">{c.name}</p>
                            </Link>
                            <br />
                            <span className="fw-normal text-secondary">{c.preDescription}</span>
                          </td>
                          <td className="text-center">{c.category.name}</td>
                          <td className="text-center">{c.videos.length} lessons</td>
                          <td className="text-center">{c.instructor.name}</td>
                          <td>${c.price}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="tab-pane" id="certificate" role="tabpanel" aria-labelledby="certificate-tab">
          <div className="p-3 d-flex gap-3">
            {certificates.map((c, i) => (
              <a href={c.source} target="_blank">
                <button key={i} className="text-primary rounded">
                  {c.course.name}
                </button>
              </a>
            ))}
          </div>
        </div>
      </div>
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(Profile)
