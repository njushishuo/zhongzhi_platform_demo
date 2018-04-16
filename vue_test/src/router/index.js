import Vue from 'vue'
import Router from 'vue-router'
import VerifyHome from '@/frameworks/auth/home'
import IsvFrame from '@/frameworks/isv/isvFrame'
import AuditorFrame from '@/frameworks/auditor/auditorFrame'
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
      path: '/login',
      name: 'Login',
      component: VerifyHome,
    },

    {
      path:'/isv/:user_id',
      name:'IsvFrame',
      component: IsvFrame,
      children:[
        {
          path: '/myApp',
          name: 'IsvAppList',
          component:IsvAppList,
        },
        {
          path: '/app/:app_id/detail',
          name: 'IsvAppDetail',
          component:IsvAppDetail,
        },
        {
          path: '/app/:app_id/apply',
          name: 'IsvAppResourceApply',
          component:IsvAppResourceApply,
        },
        {
          path: '/myOrder',
          name: 'IsvOrderList',
          component:IsvOrderList,
        },
        {
          path: '/order/:order_id',
          name: 'IsvOrderDetail',
          component:IsvOrderDetail,
        },
      ]
    },
    {
      path:'/auditor/:user_id',
      name:'AuditorFrame',
      component: AuditorFrame,
      children:[
        {
          path: '/myOrder',
          name: 'AuditorOrderList',
          component:AuditorOrderList,
        },
        {
          path: '/order/:order_id',
          name: 'AuditorOrderDetail',
          component:AuditorOrderDetail,
        },
      ]
    },
    { path: '/', redirect: '/login' },

  ]
})
