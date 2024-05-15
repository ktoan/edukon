import React, { Fragment, useEffect, useState } from 'react'
import { PageHeader } from '../component/layout'
import withBaseLogic from '../hoc/withBaseLogic'
import { showToastError, showToastSuccess } from '../util/toastAction'
import { executePayment } from '../redux/action/enrollAction'
import Loading from '../component/section/Loading'
import { fetchCourseDetail } from '../redux/action/courseAction'

const EnrollPaypal = ({ location, requiredLogin, user, navigate }) => {
  const queryParams = new URLSearchParams(location.search)
  const paymentId = queryParams.get('paymentId')
  const payerId = queryParams.get('PayerID')
  const courseId = queryParams.get('courseId')
  const [loading, setLoading] = useState(false)
  const [courseDetail, setCourseDetail] = useState(null)
  useEffect(() => {
    if (!paymentId || !payerId || !courseId) {
      navigate('/failed')
    }

    if (!user) requiredLogin()

    function next(courseDetail) {
      setCourseDetail(courseDetail)
    }
    function errorHandle(message) {
      showToastError(message)
    }
    fetchCourseDetail(courseId, next, errorHandle)
  }, [user, paymentId, payerId, courseId])

  function confirmPayment() {
    setLoading(true)
    function next() {
      showToastSuccess('User enrolled course successfully!')
      navigate(`/course/detail/${courseId}`)
      setLoading(false)
    }
    function errorHandle(error) {
      showToastError(error)
      setLoading(false)
    }
    executePayment({ payment_id: paymentId, payer_id: payerId, course_id: courseId }, next, errorHandle)
  }

  return courseDetail ? (
    <Fragment>
      <PageHeader curPage={'Confirm PayPal payment'} />
      <div className="map-address-section padding-tb section-bg">
        <div className="container">
          <div className="section-header text-center">
            <span className="subtitle">LET'S</span>
            <h2 className="text-secondary fw-normal">
              Confirm payment for <span className="fw-medium text-primary">{courseDetail.name}</span>
            </h2>
          </div>
          <div className="text-center">
            <button disabled={loading} onClick={() => confirmPayment()} className="lab-btn bg-primary text-white">
              Confirm
            </button>
          </div>
        </div>
      </div>
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(EnrollPaypal)
