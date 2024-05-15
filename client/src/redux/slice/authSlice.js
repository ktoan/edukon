import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  user: null,
  access_token: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload;
    },
    setAccessToken: (state, action) => {
      state.access_token = action.payload;
    },
  },
});

export const { setUser, setAccessToken } = authSlice.actions;
export default authSlice.reducer;
