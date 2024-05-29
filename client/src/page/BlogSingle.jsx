import { Fragment, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Footer, Header, PageHeader } from '../component/layout'
import Loading from '../component/section/Loading'
import Archive from '../component/sidebar/archive'
import Instagram from '../component/sidebar/instagram'
import PopularPost from '../component/sidebar/popular-post'
import PostCategory from '../component/sidebar/post-category'
import Search from '../component/sidebar/search'
import Tags from '../component/sidebar/tags'
import withBaseLogic from '../hoc/withBaseLogic'
import { fetchBlogDetail } from '../redux/action/blogAction'
import { showToastError } from '../util/toastAction'

const socialList = [
  {
    link: '#',
    iconName: 'icofont-facebook',
    className: 'facebook'
  },
  {
    link: '#',
    iconName: 'icofont-twitter',
    className: 'twitter'
  },
  {
    link: '#',
    iconName: 'icofont-linkedin',
    className: 'linkedin'
  },
  {
    link: '#',
    iconName: 'icofont-instagram',
    className: 'instagram'
  },
  {
    link: '#',
    iconName: 'icofont-pinterest',
    className: 'pinterest'
  }
]

const BlogSingle = () => {
  const [blogDetail, setBlogDetail] = useState(null)
  const params = useParams()
  const { blogId } = params

  useEffect(() => {
    if (blogId) {
      function next(blogDetail) {
        setBlogDetail(blogDetail)
      }
      function errorHandle(message) {
        showToastError(message)
      }
      fetchBlogDetail(blogId, next, errorHandle)
    }
  }, [blogId])

  return blogDetail ? (
    <Fragment>
      <Header />
      <PageHeader title={blogDetail.title} curPage={'Blog Detais'} />
      <div className="blog-section blog-single padding-tb section-bg">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-lg-8 col-12">
              <article>
                <div className="section-wrapper">
                  <div className="row row-cols-1 justify-content-center g-4">
                    <div className="col">
                      <div className="post-item style-2">
                        <div className="post-inner">
                          <div className="post-thumb">
                            <img src={blogDetail.thumbnail} alt="blog thumb rajibraj91" className="w-100" />
                          </div>
                          <div className="post-content">
                            <h2>{blogDetail.title}</h2>
                            <div className="meta-post">
                              <ul className="lab-ul">
                                <li>
                                  <a href="#">
                                    <i className="icofont-calendar"></i> {blogDetail.createdAt.split('T')[0]}
                                  </a>
                                </li>
                                <li>
                                  <a href="#">
                                    <i className="icofont-ui-user"></i>
                                    {blogDetail.author.name}
                                  </a>
                                </li>
                                <li>
                                  <a href="#">
                                    <i className="icofont-speech-comments"></i>
                                    {blogDetail.comments.length} Comments
                                  </a>
                                </li>
                              </ul>
                            </div>
                            <div dangerouslySetInnerHTML={{ __html: blogDetail.content }} />
                            <div className="tags-section">
                              <ul className="tags lab-ul">
                                <li>
                                  <a href="#">Agency</a>
                                </li>
                                <li>
                                  <a href="#">Business</a>
                                </li>
                                <li>
                                  <a href="#">Personal</a>
                                </li>
                              </ul>
                              <ul className="lab-ul social-icons">
                                {socialList.map((val, i) => (
                                  <li key={i}>
                                    <a href={val.link} className={val.className}>
                                      <i className={val.iconName}></i>
                                    </a>
                                  </li>
                                ))}
                              </ul>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className="authors">
                        <div className="author-thumb">
                          <img src={require('../assets/images/author/01.jpg')} alt="rajibraj91" />
                        </div>
                        <div className="author-content">
                          <h5>{blogDetail.author.name}</h5>
                          <span>{blogDetail.author.role}</span>
                          <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Nemo, cupiditate!</p>
                          <ul className="lab-ul social-icons">
                            {socialList.map((val, i) => (
                              <li key={i}>
                                <a href={val.link} className={val.className}>
                                  <i className={val.iconName}></i>
                                </a>
                              </li>
                            ))}
                          </ul>
                        </div>
                      </div>
                      <div className="comments">
                        <h4 className="title-border">{blogDetail.comments.length} reviews</h4>
                        <ul className="comment-list">
                          {blogDetail.comments.map((c, i) => (
                            <li className="comment" key={i}>
                              <div className="com-thumb">
                                <img src={require('../assets/images/author/03.jpg')} alt={c.user.name} />
                              </div>
                              <div className="com-content">
                                <div className="com-title">
                                  <div className="com-title-meta">
                                    <h6>{c.user.name}</h6>
                                    <span> {c.createdAt.split('T')[0]} </span>
                                  </div>
                                </div>
                                <p>{c.comment}</p>
                              </div>
                            </li>
                          ))}
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
              </article>
            </div>
            <div className="col-lg-4 col-12">
              <aside>
                <Search />
                <PostCategory />
                <PopularPost />
                <Archive />
                <Instagram />
                <Tags />
              </aside>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </Fragment>
  ) : (
    <Loading />
  )
}

export default withBaseLogic(BlogSingle)
