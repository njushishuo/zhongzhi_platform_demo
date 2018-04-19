import Axios from 'axios'

export default {

  getMyApps (userId) {
    return Axios.get('/isv/'+userId+'/appList')
  },

  getAppDetail(appId){
    return Axios.get('/isv/app/'+appId)
  }

}
