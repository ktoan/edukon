import React, { useState } from 'react'

const Rating = ({ value = 0, editable = false, onChange }) => {
  const totalStars = 5
  const [rating, setRating] = useState(value)

  const handleRating = (index) => {
    if (!editable) return // Prevent updating if not editable
    setRating(index + 1) // Update the rating state
    if (onChange) onChange(index + 1) // Propagate the change up if onChange handler is provided
  }

  return (
    <span className="rating">
      {[...Array(totalStars)].map((_, index) => (
        <i
          key={index}
          className="icofont-ui-rating"
          style={{ cursor: editable ? 'pointer' : 'default', color: index < rating ? '#f16126' : '#ccc' }}
          onClick={() => handleRating(index)}
        ></i>
      ))}
    </span>
  )
}

export default Rating
