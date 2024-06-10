import { Fragment, useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { showToastError, showToastSuccess } from '../util/toastAction'
import { confirmAccountRegistration, sendVerifyAccountToken } from '../redux/action/authAction'

const title = 'Verify account'
const btnText = 'Submit Now'

const VerifyAccount = () => {
  const { state } = useLocation()
  const navigate = useNavigate()

  const [loading, setLoading] = useState(false)
  const [email, setEmail] = useState(state ? state.prevEmail : '')
  const [token, setToken] = useState('')

  useEffect(() => {
    if (!state) {
      navigate('/login')
    } else {
      sendConfirmationCode()
    }
  }, [navigate, sendConfirmationCode, state])

  function sendConfirmationCode() {
    setLoading(true)
    if (email) {
      function next(message) {
        showToastSuccess(message)
        setLoading(false)
      }
      function errorHandle(message) {
        showToastError(message)
        setLoading(false)
      }
      sendVerifyAccountToken(email, next, errorHandle)
    }
  }

  function onSubmitVerifyForm(e) {
    e.preventDefault()
    setLoading(true)
    let permitSubmit = true
    if (!token) {
      showToastError('Fill the token that sent to your email.')
      permitSubmit = false
    }
    if (permitSubmit) {
      function next(message) {
        showToastSuccess(message)
        setLoading(false)
        navigate('/login')
      }
      function errorHandle(message) {
        showToastError(message)
        setLoading(false)
      }
      confirmAccountRegistration({ email, token }, next, errorHandle)
    }
  }

  return (
    <Fragment>
      <div style={{ height: '100vh' }} className="login-section padding-tb section-bg">
        <div className="container">
          <div className="account-wrapper">
            <h3 className="title">{title}</h3>
            <form onSubmit={(e) => onSubmitVerifyForm(e)} className="account-form">
              <div className="form-group">
                <input type="text" disabled={true} value={email} name="email" placeholder="Email" />
              </div>
              <div className="form-group">
                <input
                  type="text"
                  name="token"
                  onChange={(e) => setToken(e.target.value)}
                  placeholder="Confirmation Code"
                />
              </div>
              <div className="form-group text-center">
                <button disabled={loading} type="submit" className={`d-block lab-btn ${loading && `bg-dark`}`}>
                  <span>{btnText}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </Fragment>
  )
}

export default VerifyAccount
