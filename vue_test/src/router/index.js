import Vue from 'vue'
import Router from 'vue-router'
import VerifyHome from '@/frameworks/auth/home'
import Register from '@/frameworks/auth/register'
import IsvAppList from '@/frameworks/isv/appList'
import IsvAppDetail from '@/frameworks/isv/appDetail'
import IsvAppResourceApply from '@/frameworks/isv/resourceApply'
import IsvOrderList from '@/frameworks/isv/isvOrderList'
import IsvOrderDetail from '@/frameworks/isv/isvOrderDetail'
import AuditorOrderDetail from '@/frameworks/auditor/auditorOrderDetail'
import AuditorOrderList from '@/frameworks/auditor/auditorOrderList'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: VerifyHome
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/isv/:user_id/myApp',
      name: 'IsvAppList',
      component:IsvAppList,
    },
    {
      path: '/isv/:user_id/app/:app_id/detail',
      name: 'IsvAppDetail',
      component:IsvAppDetail,
    },
    {
      path: '/isv/:user_id/app/:app_id/apply',
      name: 'IsvAppResourceApply',
      component:IsvAppResourceApply,
    },
    {
      path: '/isv/:user_id/myOrder',
      name: 'IsvOrderList',
      component:IsvOrderList,
    },
    {
      path: '/isv/:user_id/order/:order_id',
      name: 'IsvOrderDetail',
      component:IsvOrderDetail,
    },
    {
      path: '/auditor/:user_id/myOrder',
      name: 'AuditorOrderList',
      component:AuditorOrderList,
    },
    {
      path: '/auditor/:user_id/order/:order_id',
      name: 'AuditorOrderDetail',
      component:AuditorOrderDetail,
    }

  ]
})
