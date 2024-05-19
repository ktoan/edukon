import { configureStore } from '@reduxjs/toolkit'
import { combineReducers } from 'redux'
import persistReducer from 'redux-persist/es/persistReducer'
import persistStore from 'redux-persist/es/persistStore'
import storage from 'redux-persist/lib/storage'
import authReducer from './slice/authSlice'
import courseReducer from './slice/courseSlice'
import blogReducer from './slice/blogSlice'

const reducer = combineReducers({
  auth: authReducer,
  course: courseReducer,
  blog: blogReducer
})

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['auth']
}

const persistRdc = persistReducer(persistConfig, reducer)

const store = configureStore({
  reducer: persistRdc,
  devTools: process.env.NODE_ENV !== 'production',
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false
    })
})

const persistor = persistStore(store)

const Store = { store, persistor }

export default Store
