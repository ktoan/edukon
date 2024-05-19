import React from 'react'
import { PuffLoader } from 'react-spinners'

const LoadingInner = () => {
  return (
    <div className="text-center">
      <PuffLoader
        color={'#ffffff'}
        loading={true}
        cssOverride={{
          margin: 'text-center'
        }}
        size={20}
        aria-label="Loading Spinner"
        data-testid="loader"
      ></PuffLoader>
    </div>
  )
}

export default LoadingInner
