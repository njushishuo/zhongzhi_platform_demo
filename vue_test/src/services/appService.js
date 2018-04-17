import Axios from 'axios'

export default {

  getMyApps (userId) {
    return Axios.get('/isv/'+userId+'/appList')
  },

}
