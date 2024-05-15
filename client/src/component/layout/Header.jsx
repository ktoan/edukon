import { useState } from 'react'
import { useSelector } from 'react-redux'
import { Link, NavLink } from 'react-router-dom'
import withBaseLogic from '../../hoc/withBaseLogic'

const phoneNumber = '+84 868 319 857'
const address = 'Ngu Hanh Son District, Da Nang City'

let socialList = [
  {
    iconName: 'icofont-facebook-messenger',
    siteLink: '#'
  },
  {
    iconName: 'icofont-twitter',
    siteLink: '#'
  },
  {
    iconName: 'icofont-vimeo',
    siteLink: '#'
  },
  {
    iconName: 'icofont-skype',
    siteLink: '#'
  },
  {
    iconName: 'icofont-rss-feed',
    siteLink: '#'
  }
]

const Header = ({ location }) => {
  const [menuToggle, setMenuToggle] = useState(false)
  const [socialToggle, setSocialToggle] = useState(false)
  const [headerFiexd, setHeaderFiexd] = useState(false)

  const { user } = useSelector((state) => state.auth)

  window.addEventListener('scroll', () => {
    if (window.scrollY > 200) {
      setHeaderFiexd(true)
    } else {
      setHeaderFiexd(false)
    }
  })

  return (
    <header className={`header-section ${headerFiexd ? 'header-fixed fadeInUp' : ''}`}>
      <div className={`header-top ${socialToggle ? 'open' : ''}`}>
        <div className="container">
          <div className="header-top-area">
            <ul className="lab-ul left">
              <li>
                <i className="icofont-ui-call"></i> <span>{phoneNumber}</span>
              </li>
              <li>
                <i className="icofont-location-pin"></i> {address}
              </li>
            </ul>
            <ul className="lab-ul social-icons d-flex align-items-center">
              <li>
                <p>Find us on : </p>
              </li>
              {socialList.map((val, i) => (
                <li key={i}>
                  <a href={val.siteLink}>
                    <i className={val.iconName}></i>
                  </a>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      <div className="header-bottom">
        <div className="container">
          <div className="header-wrapper">
            <div className="logo">
              <Link to="/">
                <img src={require('../../assets/images/logo/01.png')} alt="logo" />
              </Link>
            </div>
            <div className="menu-area">
              <div className="menu">
                <ul className={`lab-ul ${menuToggle ? 'active' : ''}`}>
                  <li>
                    <NavLink to="/">Home</NavLink>
                  </li>
                  <li>
                    <NavLink to="/about">About</NavLink>
                  </li>
                  <li>
                    <NavLink to="/course">Course</NavLink>
                  </li>
                  <li>
                    <NavLink to="/blog">Blog</NavLink>
                  </li>
                  <li>
                    <NavLink to="/contact">Contact</NavLink>
                  </li>
                </ul>
              </div>
              {!user && (
                <>
                  <Link to={`/login?redirect=${location.pathname}`} className="login">
                    <i className="icofont-user"></i> <span>LOG IN</span>
                  </Link>
                  <Link to="/sign-up" className="signup">
                    <i className="icofont-users"></i> <span>SIGN UP</span>
                  </Link>
                </>
              )}
              {user && (
                <Link to="/profile">
                  <i className="icofont-user"></i>
                  <span className="fw-bold">{user.name}</span>
                </Link>
              )}
              <div
                className={`header-bar d-lg-none ${menuToggle ? 'active' : ''}`}
                onClick={() => setMenuToggle(!menuToggle)}
              >
                <span></span>
                <span></span>
                <span></span>
              </div>
              <div className="ellepsis-bar d-lg-none" onClick={() => setSocialToggle(!socialToggle)}>
                <i className="icofont-info-square"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
  )
}

export default withBaseLogic(Header)
