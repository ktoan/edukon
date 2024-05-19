import React from 'react'

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  const pageNumbers = []

  for (let i = 1; i <= totalPages; i++) {
    pageNumbers.push(i)
  }

  return (
    <ul className="default-pagination lab-ul">
      <li>
        <button onClick={() => onPageChange(currentPage - 1)} disabled={currentPage === 1}>
          <i className="icofont-rounded-left"></i>
        </button>
      </li>
      {pageNumbers.map((number) => (
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
