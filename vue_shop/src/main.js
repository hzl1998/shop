import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
//导入字体图标
import './assets/fonts/iconfont.css'
//导入全局样式表
import './assets/css/global.css'

import {Loading} from 'element-ui'
import TreeTable from 'vue-table-with-tree-grid'

//导入富文本编辑器
import VueQuillEditor from 'vue-quill-editor'

//导入富文本编辑器对应的样式
import 'quill/dist/quill.core.css' // import styles
import 'quill/dist/quill.snow.css' // for snow theme
import 'quill/dist/quill.bubble.css' // for bubble theme

import axios from 'axios'
//配置请求的根路径
axios.defaults.baseURL = 'http://localhost:8081/shop/'
axios.interceptors.request.use(config => {
  //console.log(config)
  startLoading();
  config.headers.Authorization = window.sessionStorage.getItem('token')
  //在最后必须return config
  return config
})
axios.interceptors.response.use(config => {
  //console.log(config)
  endLoading();
  //在最后必须return config
  return config
})
Vue.prototype.$http = axios

Vue.config.productionTip = false

Vue.component('tree-table',TreeTable)
//注册为全局可用组件
Vue.use(VueQuillEditor)

Vue.use(Loading);

Vue.filter('dateFormat', function(originVal){
  const dt = new Date(originVal * 1000)
  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2,'0')
  const d = (dt.getDate()+'').padStart(2,'0')
  
  const hh = (dt.getHours()+'').padStart(2,'0')
  const mm = (dt.getMinutes()+'').padStart(2,'0')
  const ss = (dt.getSeconds()+'').padStart(2,'0')

  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
})

let loading;
function startLoading() {    //使用Element loading-start 方法
  loading = Loading.service({
    lock: true,
    text: '拼命加载中...',
    background:'rgba(255,255,255,0)',
  })
}
function endLoading() {    //使用Element loading-close 方法
  loading.close()
}

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
