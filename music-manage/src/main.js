import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/css/global.css';

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
Vue.use(ElementUI, { size: 'small' },Antd);
// Vue.use(Antd);
Vue.config.productionTip = false;



new Vue({
  router,
  render: h => h(App)
}).$mount('#app')





