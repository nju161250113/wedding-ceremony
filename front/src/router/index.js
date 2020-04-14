import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home/home'
import Login from '@/components/login/login'
import Register from '@/components/register/register'
import Consult from '@/components/consult/consult'
import SendHappiness from'@/components/happiness/sendhappiness'
Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      component: Login
    },
    {
      path:'/register',
      component:Register
    },
    {
      path:'/consult',
      component:Consult
    },
    {
      path:'/sendhappiness',
      component:SendHappiness
    }
  ]
})
