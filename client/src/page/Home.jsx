import { Fragment } from 'react'
import { About, Banner, Sponsor, Instructor, Achievement, Blog, Category, Course, Student } from '../component/section'
import { Footer, Header } from '../component/layout'

const Home = () => {
  return (
    <Fragment>
      <Header />
      <Banner />
      <Sponsor />
      <Category />
      <Course />
      <About />
      <Instructor />
      <Student />
      <Blog />
      <Achievement />
      <Footer />
    </Fragment>
  )
}

export default Home
