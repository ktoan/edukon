export const timeAgo = (dateString) => {
  const now = new Date()
  const past = new Date(dateString)
  const msPerMinute = 60 * 1000
  const msPerHour = msPerMinute * 60
  const msPerDay = msPerHour * 24
  const msPerMonth = msPerDay * 30
  const msPerYear = msPerDay * 365

  const elapsed = now - past

  if (elapsed < msPerMinute) {
    return Math.round(elapsed / 1000) + 's ago' // Seconds ago
  } else if (elapsed < msPerHour) {
    return Math.round(elapsed / msPerMinute) + 'm ago' // Minutes ago
  } else if (elapsed < msPerDay) {
    return Math.round(elapsed / msPerHour) + 'h ago' // Hours ago
  } else if (elapsed < msPerMonth) {
    return Math.round(elapsed / msPerDay) + ' days ago' // Days ago
  } else if (elapsed < msPerYear) {
    return Math.round(elapsed / msPerMonth) + ' months ago' // Months ago
  } else {
    return Math.round(elapsed / msPerYear) + ' years ago' // Years ago
  }
}
