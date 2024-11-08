// 在store.js中定义
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        isplay: false
    },
    mutations: {
        updateIsplay(state, newValue) {
            state.isplay = newValue;
        }
    }
});