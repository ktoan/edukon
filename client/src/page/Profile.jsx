import React, { Fragment, useEffect } from 'react'
import { Header, PageHeader } from '../component/layout'
import withBaseLogic from '../hoc/withBaseLogic'
import Loading from '../component/section/Loading'
import { logoutUser } from '../redux/action/authAction'
import { Link } from 'react-router-dom'

const Profile = ({ dispatch, user, requiredLogin }) => {
  useEffect(() => {
    if (!user) requiredLogin()
  }, [user, requiredLogin])

  return user ? (
    <Fragment>
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
              <div className="col-6 col-md-12">
                <h3>Information</h3>
                <form className="mb-2">
                  <div className="nf-list mb-2">
                    <input type="email" name="email" placeholder="Enter Your Email" value={user.email} disabled />
                  </div>
                  <div className="nf-list mb-2">
                    <input type="text" name="name" placeholder="Enter Your Name" value={user.name} />
                  </div>
                  <div className="nf-list mb-2">
                    <input type="text" name="phone" placeholder="Enter Your Phone" value={user.phone} />
                  </div>
                  <div className="nf-list mb-2">
                    <select value={user.gender}>
                      <option>Gender</option>
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                      <option value="DISCLOSED">Disclosed</option>
                    </select>
                  </div>
                  <button className="lab-btn text-white">Update information</button>
                </form>
                <h3>Security</h3>
                <div className="nf-list mb-2">
                  <button onClick={() => logoutUser(dispatch)} className="lab-btn bg-danger text-white">
                    Logout
                  </button>
                </div>
                <div className="nf-list mb-2">
                  <button className="lab-btn bg-secondary text-white">Lock account</button>
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
                        <th>Instructor</th>
                        <th>Price</th>
                        <th>Payment Status</th>
                        <th>Payment Method</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>1</td>
                        <td>
                          <Link to={`/view-course/1`} className="text-primary">
                            Java Core and Object Oriented Programming
                          </Link>
                        </td>
                        <td className="text-center">INSTRUCTOR</td>
                        <td>$20</td>
                        <td>
                          <div className="badge bg-success fs-6">Done</div>
                        </td>
                        <td>
                          <img
                            style={{ width: '120px' }}
                            src="https://th.bing.com/th/id/OIP.9dhEO9QY8Wwwi7gKrHh4bAHaB-?rs=1&pid=ImgDetMain"
                          />
                        </td>
                      </tr>
                      <tr>
                        <td>2</td>
                        <td>
                          <Link to={`/view-course/2`} className="text-primary">
                            C/C++ Programming for new students
                          </Link>
                        </td>
                        <td className="text-center">INSTRUCTOR</td>
                        <td>$20</td>
                        <td>
                          <div className="badge bg-success fs-6">Done</div>
                        </td>
                        <td>
                          <img
                            style={{ width: '120px' }}
                            src="https://th.bing.com/th/id/OIP.9dhEO9QY8Wwwi7gKrHh4bAHaB-?rs=1&pid=ImgDetMain"
                          />
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="tab-pane" id="certificate" role="tabpanel" aria-labelledby="certificate-tab">
          <h1>Certificate</h1>
        </div>
      </div>
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(Profile)
