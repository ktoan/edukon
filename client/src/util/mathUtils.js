export const calculateAverageRating = (reviews) => {
  const totalRating = reviews.reduce((acc, review) => acc + review.rating, 0)
  const averageRating = totalRating / reviews.length

  // Round to nearest 0.5
  return Math.round(averageRating * 2) / 2
}
