import Vue from 'vue'
import App from './App.vue'
import Main from './Main.vue'

import VueRouter from 'vue-router'
Vue.use(VueRouter);

import VueResource from 'vue-resource'
Vue.use(VueResource);

import store from './store/store'

function loadMainData(to, from, next) {
    store.dispatch('getEmployees');
    store.dispatch('getCars');
    next();
}

const router = new VueRouter({
    mode: 'history',
    base: __dirname,
    routes: [
        { path: '/', component: Main, beforeEnter: loadMainData }
    ]
});

global.router = router;

import { sync } from 'vuex-router-sync'

sync(store,router);

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
});
