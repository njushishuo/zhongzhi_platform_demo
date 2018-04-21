import Axios from 'axios'

export default {

  getResourceAppCanApply (appId) {
    return Axios.get('/resource/app/'+appId)
  },

}
