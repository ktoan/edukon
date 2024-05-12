import { Fragment, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { registerNewUser } from "../redux/action/authAction";
import { showToastError, showToastSuccess } from "../util/toastAction";

const title = "Register Now";
const socialTitle = "Register With Social Media";
const btnText = "Get Started Now";

let socialList = [
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

const SignUp = () => {
  const navigate = useNavigate();
  const [registerForm, setRegisterForm] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    full_name: "",
    gender: "",
  });

  const [loading, setLoading] = useState(false);

  function clearRegisterForm() {
    setRegisterForm({
      email: "",
      password: "",
      name: "",
      gender: "",
    });
  }

  function onChangeFormInput(e) {
    setRegisterForm({ ...registerForm, [e.target.name]: e.target.value });
  }

  function onSubmitRegisterForm(e) {
    e.preventDefault();
    setLoading(true);
    let permitSubmit = true;
    if (
      !registerForm.email ||
      !registerForm.full_name ||
      !registerForm.gender ||
      !registerForm.password ||
      !registerForm.confirmPassword
    ) {
      showToastError("All fields must be filled!");
      permitSubmit = false;
      setLoading(false);
    }
    if (registerForm.password !== registerForm.confirmPassword) {
      showToastError("Password does not match!");
      permitSubmit = false;
      setLoading(false);
    }
    if (permitSubmit) {
      function next(message) {
        showToastSuccess(message);
        setLoading(false);
        clearRegisterForm();
        navigate("/login");
      }
      function errorHandle(message) {
        showToastError(message);
        setLoading(false);
      }
      registerNewUser(registerForm, next, errorHandle);
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
              onSubmit={(e) => onSubmitRegisterForm(e)}
              className="account-form"
            >
              <div className="form-group">
                <input
                  type="email"
                  value={registerForm.email}
                  onChange={(e) => onChangeFormInput(e)}
                  name="email"
                  placeholder="Email"
                />
              </div>
              <div className="form-group">
                <input
                  type="text"
                  value={registerForm.full_name}
                  onChange={(e) => onChangeFormInput(e)}
                  name="full_name"
                  placeholder="Full Name"
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  value={registerForm.pasword}
                  onChange={(e) => onChangeFormInput(e)}
                  name="password"
                  placeholder="Password"
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  value={registerForm.confirmPassword}
                  onChange={(e) => onChangeFormInput(e)}
                  name="confirmPassword"
                  placeholder="Confirm Password"
                />
              </div>
              <div className="form-group">
                <select
                  name="gender"
                  value={registerForm.gender}
                  onChange={(e) => onChangeFormInput(e)}
                >
                  <option>Gender</option>
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                  <option value="DISCLOSED">Disclosed</option>
                </select>
              </div>
              <div className="form-group">
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
                Are you a member? <Link to="/login">Login</Link>
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

export default SignUp;
