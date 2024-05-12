import React, { Fragment } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ROUTES } from "../constant";
import { Footer, Header, ScrollToTop } from "../component/layout";
import { ErrorPage, Login, SignUp } from "../page";
import ProtectAuthenticatedRoute from "./ProtectAuthenticatedRoute";
import VerifyAccount from "../page/VerifyAccount";

const DefaultLayout = ({ children }) => {
  return (
    <Fragment>
      <Header />
      {children}
      <Footer />
    </Fragment>
  );
};

const RouteDefinition = () => {
  return (
    <Router>
      <ScrollToTop />
      <Routes>
        {ROUTES.map((item, index) => {
          const Page = item.component || Fragment;
          const Layout = item.layout != null ? item.layout : DefaultLayout;
          return (
            <Route
              key={index}
              element={
                <Layout>
                  <Page />
                </Layout>
              }
              path={item.path}
            />
          );
        })}
        <Route element={<ProtectAuthenticatedRoute />}>
          <Route path="/login" element={<Login />} />
          <Route path="/sign-up" element={<SignUp />} />
          <Route path="/verify-account" element={<VerifyAccount />} />
        </Route>
        <Route path="*" element={<ErrorPage />} />
      </Routes>
    </Router>
  );
};

export default RouteDefinition;
