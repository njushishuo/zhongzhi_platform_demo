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
import ProcessedOrderList from '@/frameworks/auditor/processedOrderList'
import UnprocessedOrderList from '@/frameworks/auditor/unprocessedOrderList'

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
          path: '/isv/myApp',
          name: 'IsvAppList',
          component:IsvAppList,
        },
        {
          path: '/isv/app/:app_id',
          name: 'IsvAppDetail',
          component:IsvAppDetail,
        },
        {
          path: '/isv/app/:app_id/apply',
          name: 'IsvAppResourceApply',
          component:IsvAppResourceApply,
        },
        {
          path: '/isv/myOrder',
          name: 'IsvOrderList',
          component:IsvOrderList,
        },
        {
          path: '/isv/order/:order_id',
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
          path: '/auditor/order/wait_review',
          name: 'UnprocessedOrderList',
          component:UnprocessedOrderList,
        },
        {
          path: '/auditor/order/processed',
          name: 'ProcessedOrderList',
          component:ProcessedOrderList,
        },
        {
          path: '/auditor/order/:order_id',
          name: 'AuditorOrderDetail',
          component:AuditorOrderDetail,
        },
      ]
    },
    { path: '/', redirect: '/login' },

  ]
})
