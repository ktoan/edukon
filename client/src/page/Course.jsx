import { Fragment, useEffect } from 'react'
import { Link } from 'react-router-dom'
import Footer from '../component/layout/Footer'
import Header from '../component/layout/Header'
import PageHeader from '../component/layout/PageHeader'
import GroupSelect from '../component/sidebar/group-select'
import Pagination from '../component/sidebar/pagination'
import Rating from '../component/sidebar/Rating'
import SkillSelect from '../component/sidebar/skill-select'
import { useDispatch, useSelector } from 'react-redux'
import { fetchAllCourses } from '../redux/action/courseAction'

const courseList = [
  {
    imgUrl: 'assets/images/course/01.jpg',
    imgAlt: 'course rajibraj91 rajibraj',
    price: '$30',
    cate: 'Adobe XD',
    reviewCount: '03 reviews',
    title: 'Fundamentals of Adobe XD Theory Learn New',
    totalLeson: '18x Lesson',
    schdule: 'Online Class',
    authorImgUrl: 'assets/images/course/author/01.jpg',
    authorImgAlt: 'course author rajibraj91 rajibraj',
    authorName: 'William Smith',
    btnText: 'Read More'
  },
  {
    imgUrl: 'assets/images/course/02.jpg',
    imgAlt: 'course rajibraj91 rajibraj',
    price: '$30',
    cate: 'Adobe XD',
    reviewCount: '03 reviews',
    title: 'Certified Graphic Design with Free Project Course',
    totalLeson: '18x Lesson',
    schdule: 'Online Class',
    authorImgUrl: 'assets/images/course/author/02.jpg',
    authorImgAlt: 'course author rajibraj91 rajibraj',
    authorName: 'Lora Smith',
    btnText: 'Read More'
  },
  {
    imgUrl: 'assets/images/course/03.jpg',
    imgAlt: 'course rajibraj91 rajibraj',
    price: '$30',
    cate: 'Adobe XD',
    reviewCount: '03 reviews',
    title: 'Theory Learn New Student And Fundamentals',
    totalLeson: '18x Lesson',
    schdule: 'Online Class',
    authorImgUrl: 'assets/images/course/author/03.jpg',
    authorImgAlt: 'course author rajibraj91 rajibraj',
    authorName: 'Robot Smith',
    btnText: 'Read More'
  },
  {
    imgUrl: 'assets/images/course/04.jpg',
    imgAlt: 'course rajibraj91 rajibraj',
    price: '$30',
    cate: 'Adobe XD',
    reviewCount: '03 reviews',
    title: 'Computer Fundamentals Basic Startup Ultricies Vitae',
    totalLeson: '18x Lesson',
    schdule: 'Online Class',
    authorImgUrl: 'assets/images/course/author/04.jpg',
    authorImgAlt: 'course author rajibraj91 rajibraj',
    authorName: 'Zinat Zaara',
    btnText: 'Read More'
  },
  {
    imgUrl: 'assets/images/course/05.jpg',
    imgAlt: 'course rajibraj91 rajibraj',
    price: '$30',
    cate: 'Adobe XD',
    reviewCount: '03 reviews',
    title: 'Boozy Halloween Drinks for the Grown Eleifend Kuismod',
    totalLeson: '18x Lesson',
    schdule: 'Online Class',
    authorImgUrl: 'assets/images/course/author/05.jpg',
    authorImgAlt: 'course author rajibraj91 rajibraj',
    authorName: 'Rajib Raj',
    btnText: 'Read More'
  },
  {
    imgUrl: 'assets/images/course/06.jpg',
    imgAlt: 'course rajibraj91 rajibraj',
    price: '$30',
    cate: 'Adobe XD',
    reviewCount: '03 reviews',
    title: 'Student Want to Learn About Science And Arts',
    totalLeson: '18x Lesson',
    schdule: 'Online Class',
    authorImgUrl: 'assets/images/course/author/06.jpg',
    authorImgAlt: 'course author rajibraj91 rajibraj',
    authorName: 'Angel Mili',
    btnText: 'Read More'
  }
]

const CoursePage = () => {
  const dispatch = useDispatch()
  const { courses } = useSelector((state) => state.course)

  useEffect(() => {
    fetchAllCourses(dispatch)
  }, [])
  return (
    <Fragment>
      <Header />
      <PageHeader title={'Archives: Courses'} curPage={'Courses'} />
      <GroupSelect />
      <div className="course-section padding-tb section-bg">
        <div className="container">
          <div className="section-wrapper">
            <div className="course-showing-part">
              <div className="d-flex flex-wrap align-items-center justify-content-between">
                <div className="course-showing-part-left">
                  <p>Showing 1-6 of 10 results</p>
                </div>
                <div className="course-showing-part-right d-flex flex-wrap align-items-center">
                  <span>Sort by :</span>
                  <div className="select-item">
                    <SkillSelect select={'all'} />
                    <div className="select-icon">
                      <i className="icofont-rounded-down"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="row g-4 justify-content-center row-cols-xl-3 row-cols-md-2 row-cols-1">
              {courses.map((course, i) => (
                <div className="col" key={i}>
                  <div className="course-item">
                    <div className="course-inner">
                      <div className="course-thumb" style={{ height: '200px' }}>
                        <img src={course.thumbnail} alt={`${course.title} image`} />
                      </div>
                      <div className="course-content">
                        <div className="course-price">{course.price}</div>
                        <div className="course-category">
                          <div className="course-cate">
                            <a href="#">{course.category.name}</a>
                          </div>
                          <div className="course-reiew">
                            <Rating />
                          </div>
                        </div>
                        <Link to="/course-single">
                          <h4>{course.name}</h4>
                        </Link>
                        <div className="course-details">
                          <div className="couse-count">
                            <i className="icofont-video-alt"></i> {course.videos.length} lessons
                          </div>
                          <div className="couse-topic">
                            <i className="icofont-signal"></i> Online Class
                          </div>
                        </div>
                        <div className="course-footer">
                          <div className="course-author">
                            <img
                              src={
                                course.instructor.gender === 'MALE'
                                  ? 'assets/images/course/author/05.jpg'
                                  : 'assets/images/course/author/06.jpg'
                              }
                              alt={`Course img`}
                            />
                            <Link to="/team-single" className="ca-name">
                              {course.instructor.name}
                            </Link>
                          </div>
                          <div className="course-btn">
                            <Link to={`/course/detail/${course.id}`} className="lab-btn-text">
                              Know more
                              <i className="icofont-external-link"></i>
                            </Link>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
            <Pagination />
          </div>
        </div>
      </div>
      <Footer />
    </Fragment>
  )
}

export default CoursePage
