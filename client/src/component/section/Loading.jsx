import React from 'react'
import { PuffLoader } from 'react-spinners'
const Loading = () => {
  return (
    <div
      style={{ height: '100vh', justifyContent: 'center', alignItems: 'center' }}
      className="padding-tb section-bg bg-white d-flex"
    >
      <div style={{ textAlign: 'center' }}>
        <PuffLoader
          color={'#f16126'}
          loading={true}
          cssOverride={{
            margin: 'text-center'
          }}
          size={50}
          aria-label="Loading Spinner"
          data-testid="loader"
        />
      </div>
    </div>
  )
}

export default Loading
