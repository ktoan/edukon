import React, { Fragment } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ROUTES } from "../constant";
import { Footer, Header, ScrollToTop } from "../component/layout";
import { ErrorPage } from "../page";

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
        <Route path="*" element={<ErrorPage />} />
      </Routes>
    </Router>
  );
};

export default RouteDefinition;
