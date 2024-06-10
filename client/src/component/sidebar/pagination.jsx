import React from 'react'

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  const getVisiblePageNumbers = () => {
    let startPage, endPage

    if (totalPages <= 3) {
      // If total pages are less than or equal to 3, show all pages
      startPage = 1
      endPage = totalPages
    } else {
      if (currentPage <= 2) {
        startPage = 1
        endPage = 3
      } else if (currentPage + 1 >= totalPages) {
        startPage = totalPages - 2
        endPage = totalPages
      } else {
        startPage = currentPage - 1
        endPage = currentPage + 1
      }
    }

    const pageNumbers = []
    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(i)
    }
    return pageNumbers
  }

  const visiblePageNumbers = getVisiblePageNumbers()

  return (
    <ul className="default-pagination lab-ul">
      <li>
        <button onClick={() => onPageChange(currentPage - 1)} disabled={currentPage === 1}>
          <i className="icofont-rounded-left"></i>
        </button>
      </li>
      {visiblePageNumbers.map((number) => (
        <li key={number}>
          <button onClick={() => onPageChange(number)} className={number === currentPage ? 'active' : ''}>
            {number}
          </button>
        </li>
      ))}
      <li>
        <button onClick={() => onPageChange(currentPage + 1)} disabled={currentPage === totalPages}>
          <i className="icofont-rounded-right"></i>
        </button>
      </li>
    </ul>
  )
}

export default Pagination
