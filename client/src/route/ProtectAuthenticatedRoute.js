import React from "react";
import { useSelector } from "react-redux";
import { Navigate, Outlet, useSearchParams } from "react-router-dom";

const ProtectAuthenticatedRoute = () => {
  const { user } = useSelector((state) => state.auth);
  const [param] = useSearchParams()
  const redirectTo = param.get("redirect");
  return !user ? <Outlet /> : <Navigate to={redirectTo || "/"} />;
};

export default ProtectAuthenticatedRoute;
