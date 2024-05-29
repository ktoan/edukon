import { useEffect } from 'react'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import withBaseLogic from '../../hoc/withBaseLogic'
import { fetchAllBlogs } from '../../redux/action/blogAction'

const subTitle = 'FORM OUR BLOG POSTS'
const title = 'More Articles From Resource Library'

const Blog = ({ dispatch }) => {
  const { blogs } = useSelector((state) => state.blog)

  useEffect(() => {
    fetchAllBlogs(dispatch)
  }, [dispatch])

  return (
    <div className="blog-section padding-tb section-bg">
      <div className="container">
        <div className="section-header text-center">
          <span className="subtitle">{subTitle}</span>
          <h2 className="title">{title}</h2>
        </div>
        <div className="section-wrapper">
          <div className="row row-cols-1 row-cols-md-2 row-cols-xl-3 justify-content-center g-4">
            {[...blogs]
              .reverse()
              .splice(0, 3)
              .map((blog, i) => (
                <div className="col" key={i}>
                  <div className="post-item">
                    <div className="post-inner">
                      <div className="post-thumb">
                        <Link to={`/blog/detail/${blog.id}`}>
                          <img src={blog.thumbnail} alt={blog.title} />
                        </Link>
                      </div>
                      <div className="post-content">
                        <Link to={`/blog/detail/${blog.id}`}>
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
                          <Link to={`/blog/detail/${blog.id}`} className="lab-btn-text">
                            Read more <i className="icofont-external-link"></i>
                          </Link>
                        </div>
                        <div className="pf-right">
                          <i className="icofont-commentt"></i>
                          <span className="comment-count">{blog.comments.length} Comments</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
          </div>
        </div>
      </div>
    </div>
  )
}

export default withBaseLogic(Blog)
