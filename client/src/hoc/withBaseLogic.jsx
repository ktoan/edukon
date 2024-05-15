import { useDispatch, useSelector } from 'react-redux'
import { useLocation, useNavigate } from 'react-router-dom'
const withBaseLogic = (Component) => (props) => {
  const navigate = useNavigate()
  const location = useLocation()
  const dispatch = useDispatch()
  const { user } = useSelector((state) => state.auth)

  function requiredLogin() {
    if (!user) {
      const searchParams = new URLSearchParams(location.search)
      searchParams.set('redirect', location.pathname + location.search)
      navigate({
        pathname: '/login',
        search: searchParams.toString()
      })
    }
  }

  return (
    <Component
      {...props}
      user={user}
      navigate={navigate}
      location={location}
      dispatch={dispatch}
      requiredLogin={requiredLogin}
    />
  )
}

export default withBaseLogic
