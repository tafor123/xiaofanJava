import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI, { size: 'small' });

import '@/assets/css/global.css'
import '@/assets/iconfont/iconfont.css'
import '@/assets/theme/index.css'
import request from "@/utils/request";



Vue.config.productionTip = false
Vue.prototype.$request=request

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
