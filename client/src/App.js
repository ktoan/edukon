import "swiper/css";
import "react-toastify/dist/ReactToastify.css";
import { Fragment, useEffect } from "react";
import { RouteDefinition } from "./route";
import { ToastContainer } from "react-toastify";
import { useDispatch, useSelector } from "react-redux";
import { setAuthToken } from "./util/setAuthToken";
import { loadUser } from "./redux/action/authAction";

function App() {
  const dispatch = useDispatch();
  const { access_token } = useSelector((state) => state.auth);

  useEffect(() => {
    setAuthToken(access_token);
    loadUser(dispatch);
  }, [dispatch, access_token]);
  return (
    <Fragment>
      <RouteDefinition />
      <ToastContainer />
    </Fragment>
  );
}

export default App;
