import { Fragment } from "react";
import {
  About,
  Banner,
  Sponsor,
  Instructor,
  Achievement,
  Blog,
  Category,
  Course,
  Student,
} from "../component/section";

const Home = () => {
  return (
    <Fragment>
      <Banner />
      <Sponsor />
      <Category />
      <Course />
      <About />
      <Instructor />
      <Student />
      <Blog />
      <Achievement />
    </Fragment>
  );
};

export default Home;
