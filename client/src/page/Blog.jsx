import React, { Fragment, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import Footer from '../component/layout/Footer'
import Header from '../component/layout/Header'
import PageHeader from '../component/layout/PageHeader'
import Pagination from '../component/sidebar/pagination'
import { fetchAllBlogs } from '../redux/action/blogAction'

const BlogPage = () => {
  const dispatch = useDispatch()
  const { blogs } = useSelector((state) => state.blog)
  const [currentPage, setCurrentPage] = useState(1)
  const blogsPerPage = 3

  useEffect(() => {
    fetchAllBlogs(dispatch)
  }, [dispatch])

  // Get current blogs
  const indexOfLastBlog = currentPage * blogsPerPage
  const indexOfFirstBlog = indexOfLastBlog - blogsPerPage
  const currentBlogs = blogs.slice(indexOfFirstBlog, indexOfLastBlog)

  // Change page
  const handlePageChange = (pageNumber) => setCurrentPage(pageNumber)

  return (
    <Fragment>
      <Header />
      <PageHeader title={'Our Blog Posts'} curPage={'Blogs'} />
      <div className="blog-section padding-tb section-bg">
        <div className="container">
          <div className="section-wrapper">
            <div className="row row-cols-1 row-cols-md-2 row-cols-xl-3 justify-content-center g-4">
              {currentBlogs.map((blog, i) => (
                <div className="col" key={i}>
                  <div className="post-item">
                    <div className="post-inner">
                      <div className="post-thumb">
                        <Link to="/blog-single">
                          <img src={blog.thumbnail} alt={blog.title} />
                        </Link>
                      </div>
                      <div className="post-content">
                        <Link to="/blog-single">
                          <h4>{blog.title}</h4>
                        </Link>
                        <div className="meta-post">
                          <ul className="lab-ul">
                            <li>
                              <i className="icofont-ui-user"></i>
                              {blog.author.name}
                            </li>
                            <li>
                              <i className="icofont-calendar"></i>
                              {blog.createdAt.split('T')[0]}
                            </li>
                          </ul>
                        </div>
                        <p>{blog.preDescription}</p>
                      </div>
                      <div className="post-footer">
                        <div className="pf-left">
                          <Link to="/blog-single" className="lab-btn-text">
                            Read more <i className="icofont-external-link"></i>
                          </Link>
                        </div>
                        <div className="pf-right">
                          <i className="icofont-comment"></i>
                          <span className="comment-count">{blog.comments.length} Comments</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
            <Pagination
              currentPage={currentPage}
              totalPages={Math.ceil(blogs.length / blogsPerPage)}
              onPageChange={handlePageChange}
            />
          </div>
        </div>
      </div>
      <Footer />
    </Fragment>
  )
}

export default BlogPage
