import Axios from 'axios'

export default {

  data:{
    loggedIn : false
  },

  login (params) {
    return Axios.post('/login', params)
  },

  register (params) {
    return Axios.post('/register', params)
  },

  setLogin(){
    this.data.loggedIn= true;
  },

  setLogOut(){
    this.data.loggedIn = false;
  },

  isLoggedIn(){
    return this.data.loggedIn;
  }

}
