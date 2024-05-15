import React from 'react'

const Rating = ({ value = 0 }) => {
  // Create an array with 5 elements [0, 1, 2, 3, 4]
  const totalStars = 5

  return (
    <span className="ratting">
      {[...Array(totalStars)].map((star, index) => (
        <i
          key={index}
          className="icofont-ui-rating"
          style={{ color: index < value ? '#f16126' : '#ccc' }} // Color the star if its index is less than the value
        ></i>
      ))}
    </span>
  )
}

export default Rating
