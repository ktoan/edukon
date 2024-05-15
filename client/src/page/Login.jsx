import { Fragment, useState } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { loginUser } from "../redux/action/authAction";
import { showToastError, showToastSuccess } from "../util/toastAction";
import withBaseLogic from "../hoc/withBaseLogic";

const title = "Login";
const socialTitle = "Login With Social Media";
const btnText = "Submit Now";

const socialList = [
  {
    link: "#",
    iconName: "icofont-facebook",
    className: "facebook",
  },
  {
    link: "#",
    iconName: "icofont-twitter",
    className: "twitter",
  },
  {
    link: "#",
    iconName: "icofont-linkedin",
    className: "linkedin",
  },
  {
    link: "#",
    iconName: "icofont-instagram",
    className: "instagram",
  },
  {
    link: "#",
    iconName: "icofont-pinterest",
    className: "pinterest",
  },
];

const Login = ({ navigate, dispatch }) => {
  const [loginForm, setLoginForm] = useState({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);

  function clearLoginForm() {
    setLoginForm({
      email: "",
      password: "",
    });
  }

  function onChangeFormInput(e) {
    setLoginForm({ ...loginForm, [e.target.name]: e.target.value });
  }

  function onSubmitLoginForm(e) {
    e.preventDefault();
    setLoading(true);
    let permitSubmit = true;
    if (!loginForm.email || !loginForm.password) {
      permitSubmit = false;
      showToastError("All fields must be filled!");
      setLoading(false);
    }
    if (permitSubmit) {
      function next() {
        clearLoginForm();
        setLoading(false);
      }
      function errorHandle(error) {
        setLoading(false);
        showToastError(
          error.response ? error.response.data.message : error.message
        );
        if (error.response.data.statusCode === 403) {
          navigate("/verify-account", {
            state: { prevEmail: loginForm.email },
          });
        }
      }
      loginUser(dispatch, loginForm, next, errorHandle);
    }
  }

  return (
    <Fragment>
      <div
        style={{ height: "100vh" }}
        className="login-section padding-tb section-bg"
      >
        <div className="container">
          <div className="account-wrapper">
            <h3 className="title">{title}</h3>
            <form
              onSubmit={(e) => onSubmitLoginForm(e)}
              className="account-form"
            >
              <div className="form-group">
                <input
                  type="text"
                  value={loginForm.email}
                  onChange={(e) => onChangeFormInput(e)}
                  name="email"
                  placeholder="Email"
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  value={loginForm.password}
                  onChange={(e) => onChangeFormInput(e)}
                  name="password"
                  placeholder="Password"
                />
              </div>
              <div className="form-group">
                <div className="d-flex justify-content-between flex-wrap pt-sm-2">
                  <div className="checkgroup">
                    <input type="checkbox" name="remember" id="remember" />
                    <label htmlFor="remember">Remember Me</label>
                  </div>
                  <Link to="/forgetpass">Forget Password?</Link>
                </div>
              </div>
              <div className="form-group text-center">
                <button
                  disabled={loading}
                  type="submit"
                  className={`d-block lab-btn ${loading && `bg-dark`}`}
                >
                  <span>{btnText}</span>
                </button>
              </div>
            </form>
            <div className="account-bottom">
              <span className="d-block cate pt-10">
                Don’t Have any Account? <Link to="/sign-up">Sign Up</Link>
              </span>
              <span className="or">
                <span>or</span>
              </span>
              <h5 className="subtitle">{socialTitle}</h5>
              <ul className="lab-ul social-icons justify-content-center">
                {socialList.map((val, i) => (
                  <li key={i}>
                    <a href={val.link} className={val.className}>
                      <i className={val.iconName}></i>
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default withBaseLogic(Login);
