import Axios from 'axios'

export default {

  isvGetMyOrders (userId) {
    return Axios.get('/isv/'+userId+'/workOrderList')
  },

  isvGetOrderDetail(woId){
    return Axios.get('/isv/workOrder/'+woId)
  },

  createOrder(params){
    return Axios.post('/workOrder',params)
  },

  auditorGetMyOrders(userId,status){
    return Axios.get('/auditor/'+userId+'/workOrderList'+'?status='+status)
  },

  auditorGetOrderDetail(userId,woId){
    return Axios.get('/auditor/'+userId+'/workOrder/'+woId)
  },

  review(userId,para){
    return Axios.post('/auditor/'+userId+'/review',para)
  }
}
