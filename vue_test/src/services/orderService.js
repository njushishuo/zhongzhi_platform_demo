import Axios from 'axios'

export default {

  isvGetMyOrders (userId) {
    return Axios.get('/isv/'+userId+'/workOrderList')
  },

  isvGetOrderDetail(woId){
    return Axios.get('/isv/workorder/'+woId)
  },

  createOrder(params){
    return Axios.post('/workOrder',params)
  }

}
