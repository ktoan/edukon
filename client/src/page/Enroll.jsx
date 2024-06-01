import React, { Fragment, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import withBaseLogic from '../hoc/withBaseLogic'
import { fetchCourseDetail } from '../redux/action/courseAction'
import { createPayment } from '../redux/action/enrollAction'
import { showToastError, showToastSuccess } from '../util/toastAction'

const Enroll = ({ user, requiredLogin }) => {
  const [courseDetail, setCourseDetail] = useState(null)
  const [loading, setLoading] = useState(false)
  const params = useParams()
  const { courseId } = params
  const [paymentMethod, setPaymentMethod] = useState('PAYPAL')
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
  useEffect(() => {
    if (!user) requiredLogin()
  }, [user])

  function onClickCreatePayment() {
    if (!loading) {
      setLoading(true)
      function next(message, link) {
        showToastSuccess(message)
        window.location.href = link
        setLoading(false)
      }
      function errorHandle(message) {
        showToastError(message)
        setLoading(false)
      }
      createPayment({ course_id: courseId, payment_method: paymentMethod }, next, errorHandle)
    }
  }
  return courseDetail ? (
    <Fragment>
      <PageHeader title={`Enroll ${courseDetail.name}`} curPage={'Enroll'} />
      <div className="map-address-section padding-tb section-bg">
        <div className="container">
          <div className="section-header text-center">
            <span className="subtitle">LET'S</span>
            <h2 className="text-secondary fw-normal">
              Check out ${courseDetail.price} for enjoy <span className="text-dark fw-bold">{courseDetail.name}</span>
            </h2>
          </div>
          <div className="section-wrapper">
            <div className="row flex-row-reverse">
              <div className="col-xl-6 col-lg-5 col-12">
                <div className="contact-wrapper">
                  <div
                    onClick={() => setPaymentMethod('MOMO')}
                    style={{ border: `5px solid ${paymentMethod === 'MOMO' ? `black` : `white`}` }}
                    className="contact-item"
                  >
                    <div className="contact-thumb">
                      <img
                        style={{ borderRadius: '10px' }}
                        src="https://cdn.haitrieu.com/wp-content/uploads/2022/10/Logo-MoMo-Square-1024x1024.png"
                      />
                    </div>
                    <div className="contact-content">
                      <h6 className="title">Pay by MOMO</h6>
                      <p>Check out your course with MoMo</p>
                    </div>
                  </div>
                  <div
                    onClick={() => setPaymentMethod('STRIPE')}
                    style={{ border: `5px solid ${paymentMethod === 'STRIPE' ? `black` : `white`}` }}
                    className="contact-item"
                  >
                    <div className="contact-thumb">
                      <img
                        style={{ borderRadius: '10px' }}
                        src="https://clipartcraft.com/images/stripe-logo-two-5.png"
                      />
                    </div>
                    <div className="contact-content">
                      <h6 className="title">Pay by STRIPE</h6>
                      <p>Check out your course with Stripe</p>
                    </div>
                  </div>
                  <div
                    onClick={() => setPaymentMethod('PAYPAL')}
                    style={{ border: `5px solid ${paymentMethod === 'PAYPAL' ? `black` : `white`}` }}
                    className="contact-item"
                  >
                    <div className="contact-thumb">
                      <img
                        style={{ borderRadius: '10px' }}
                        src="https://androidapksfree.com/wp-content/uploads/2015/10/PayPal-APK-2.png"
                      />
                    </div>
                    <div className="contact-content">
                      <h6 className="title">Pay by PAYPAL</h6>
                      <p>Check out your course with PayPal</p>
                    </div>
                  </div>
                  <div className="course-enroll">
                    <button
                      onClick={() => onClickCreatePayment()}
                      style={{ cursor: 'pointer' }}
                      to=""
                      className="lab-btn bg-primary"
                    >
                      <span>{loading ? `Loading...` : `Create Payment`}</span>
                    </button>
                  </div>
                </div>
              </div>
              <div className="col-xl-6 col-lg-7 col-12">
                <div className="sf-left">
                  <div className="sfl-thumb">
                    <img src={require('../assets/images/feedback/01.jpg')} alt="student feedback" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(Enroll)
