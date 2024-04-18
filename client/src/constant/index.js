import {
  AboutPage,
  BlogPage,
  BlogSingle,
  ContactPage,
  CoursePage,
  CourseSingle,
  CourseView,
  HomePage,
  Login,
  SavedPage,
  SignUp,
} from "../page";

export const ROUTES = [
  { name: "Home", path: "/", component: HomePage, isShowHeader: true },
  { name: "About", path: "/about", component: AboutPage, isShowHeader: true },
  {
    name: "Courses",
    path: "/course",
    component: CoursePage,
    isShowHeader: true,
  },
  { name: "Blogs", path: "/blog", component: BlogPage, isShowHeader: true },
  { name: "Blog Single", path: "/blog-single", component: BlogSingle },
  {
    name: "Contact",
    path: "/contact",
    component: ContactPage,
    isShowHeader: true,
  },
  { name: "Course Single", path: "/course-single", component: CourseSingle },
  { name: "Course View", path: "/course-view", component: CourseView },
  { name: "Saved Page", path: "/saved", component: SavedPage },
  { name: "Login Page", path: "/login", component: Login },
  { name: "Sign Up Page", path: "/sign-up", component: SignUp },
];
