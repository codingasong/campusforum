import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import Layoutq from "@/layout/Layoutq";

const routes = [
  {
    path: '/a',
    name: 'Layout',
    component: Layout,
    redirect: "/a/goods",
    children: [
      {
        path: 'user',
        name: 'User',
        component: () =>import("@/views/User"),
      },
      {
        path: 'major',
        name: 'Major',
        component: () =>import("@/views/Major"),
      },
      {
        path: 'posts',
        name: 'Posts',
        component: () =>import("@/views/Posts"),
      },
      {
        path: 'job',
        name: 'Job',
        component: () =>import("@/views/Job"),
      },
      {
        path: 'goods',
        name: 'Goods',
        component: () =>import("@/views/Goods"),
      },
      {
        path: 'person',
        name: 'Person',
        component: () =>import("@/views/Person"),
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login"),

  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register"),

  },
  {
    path: '/',
    name: 'Layoutq',
    component: Layoutq,
    redirect: "/index",
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () =>import("@/views/Index"),
      },
      {
        path: 'postFront',
        name: 'PostFront',
        component: () =>import("@/views/PostFront"),
      },
      {
        path: 'jobFront',
        name: 'JobFront',
        component: () =>import("@/views/JobFront"),
      },
      {
        path: 'detail',
        name: 'Deatil',
        component: () =>import("@/views/Detail"),
      },
      {
        path: 'postDetail',
        name: 'PostDetail',
        component: () =>import("@/views/PostDetail"),
      },
      {
        path: 'jobDetail',
        name: 'JobDetil',
        component: () =>import("@/views/JobDetail"),
      }
    ],
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
