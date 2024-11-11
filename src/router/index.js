import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../index.vue'
import LoginView from '../views/LoginView.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/toRegister',
    name: 'register',
    component: () => import('../views/RegisterView.vue')

  },

  {
    path: '/',
    name: 'Index',
    component: Index,
    children: [
      { path: 'user', name: 'UserView', component: () => import('../views/manage/UserView') },
      { path: 'crypto', name: 'XunibiView', component: () => import('../views/manage/XunibiView.vue') },
      { path: 'rent', name: "RentView", component: () => import('../views/manage/RentView.vue') },
      { path: 'return', name: "ReturnView", component: () => import('../views/manage/ReturnView.vue') },
      { path: 'team', name: "TeamView", component: () => import("../views/manage/TeamView.vue") }
    ]
  },

  {
    path: '/login',
    name: 'LoginView',
    component: LoginView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
