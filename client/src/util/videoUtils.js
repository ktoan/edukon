import React, { useState, useEffect, useRef } from 'react'

export const VideoDuration = ({ videoUrl }) => {
  const [duration, setDuration] = useState(null)
  const videoRef = useRef(null)

  useEffect(() => {
    const videoElement = videoRef.current

    const handleLoadedMetadata = () => {
      setDuration(videoElement.duration)
    }

    const handleError = () => {
      console.error('Failed to load video metadata')
    }

    if (videoElement) {
      videoElement.addEventListener('loadedmetadata', handleLoadedMetadata)
      videoElement.addEventListener('error', handleError)
      videoElement.src = videoUrl
      videoElement.load()

      return () => {
        videoElement.removeEventListener('loadedmetadata', handleLoadedMetadata)
        videoElement.removeEventListener('error', handleError)
      }
    }
  }, [videoUrl])

  return duration !== null ? <span>Video duration: {duration} seconds</span> : <span>Loading video duration...</span>
}
