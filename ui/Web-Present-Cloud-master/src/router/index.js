import Vue from 'vue'
import Router from 'vue-router'
import Home from 'pages/home'
import Login from 'pages/login'
import Welcome from '@/components/Welcome/welcome.vue'
import MenuManage from '@/components/MenuManage/menuManage.vue'
import Users from '@/components/Users/users.vue'
import Authority from '@/components/Authority/authority.vue'
import dataDictionaryManage from '@/components/DataDictionary/dataDictionaryManage.vue'
import dataDictionaryInput from '@/components/DataDictionary/dataDictionaryInput'
import dataDictionaryEdit from '@/components/DataDictionary/dataDictionaryEdit'
import parameterSet from '@/components/ParameterSet/parameterSet'
import Courses from '@/components/Courses/courses.vue'
import CourseInfo from '@/components/Courses/courseinfo.vue'
import Members from '@/components/Courses/members.vue'
import Role from '@/components/Roles/roles'
import organizationManage from '@/components/OrganizationManage/organizationManage'
import E_403 from '@/pages/error-page/403'
import E_404 from '@/pages/error-page/404'
import E_500 from '@/pages/error-page/500'
import Register from 'pages/Register'
import Forgetpasword from 'pages/Forgetpasword'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/403',
      component: E_403
    },
    {
      path: '/404',
      component: E_404
    },
    {
      path: '/500',
      component: E_500
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/Register',
      component: Register
    },
    {
      path: '/Forgetpasword',
      component: Forgetpasword
    },
    {
      path: '/',
      name: 'Home',
      component: Home,
      redirect: '/welcome',
      children: [
        {
          name: 'welcome',
          path: '/welcome',
          component: Welcome
        }, {
          name: 'MenuManage',
          path: '/menus-list',
          component: MenuManage
        },
        {
          name: 'Users',
          path: '/users-list',
          component: Users
        }, {
          name: 'Roles',
          path: '/roles-list',
          component: Role
        },
        {
          name: 'Authority',
          path: '/authority-list',
          component: Authority
        },
        {
          name: 'dataDictionaryManage',
          path: '/data-dict-manage',
          component: dataDictionaryManage
        },
        {
          name: 'dataDictionaryInput',
          path: '/data-dict-input',
          component: dataDictionaryInput
        },
        {
          name: 'dataDictionaryEdit',
          path: '/data-dict-edit',
          component: dataDictionaryEdit
        },
        {
          name: 'parameterSet',
          path: '/parameterSet',
          component: parameterSet
        },
        {
          name: 'Courses',
          path: '/courses-list',
          component: Courses

        },
        {
          name: 'CourseInfo',
          path: '/course-info',
          component: CourseInfo
        },
        {
          name: 'Members',
          path: '/members',
          component: Members
        },
        {
          name: 'organizationManage',
          path: '/organization_list',
          component: organizationManage
        }
      ]
    },
    {
      path: '/404',
      component: () => import('pages/error-page/404'),
      hidden: true
    },
    {
      path: '/403',
      component: () => import('pages/error-page/403'),
      hidden: true
    },
    {
      path: '/500',
      component: () => import('pages/error-page/500'),
      hidden: true
    }
  ]
})

// 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to 将要访问的路径
//   // from 代表从哪个路径跳转而来
//   // next 是一个函数 表示方放行
//   // next() 放行 next('/login') 强制跳转
//
//   if (to.path === '/login' || to.path === '/Forgetpasword' || to.path === '/Register') { return next() }
//   // 获取token
//   const tokenStr = window.localStorage.getItem('token')
//   if (!tokenStr) { return next('/login') }
//   next()
// })

export default router
