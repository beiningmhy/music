import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/css/global.css';
// import globalMixin from './globalMixin';
import store from './store'

// import Antd from 'ant-design-vue';
// import 'ant-design-vue/dist/antd.css';
Vue.use(ElementUI, { size: 'small' });
// Vue.use(Antd);
Vue.config.productionTip = false;
// Vue.mixin(globalMixin)
// Vue.prototype.$play = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')