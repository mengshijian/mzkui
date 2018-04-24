import Vue from 'vue'
import Router from 'vue-router'
import Cookie from 'vue-cookie'
Vue.use(Router)
Vue.use(Cookie)

export const router = new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: resolve => require(['../components/login.vue'], resolve)
    },
    {
      path: '/',
      name: 'index',
      component: resolve => require(['../components/index.vue'], resolve),
      redirect: '/loglist',
      children: [
        {
          path: 'loglist',
          name: 'loglist',
          component: resolve => require(['../components/logList.vue'], resolve)
        },
        {
          path: 'record',
          name: 'record',
          component: resolve => require(['../components/retrievalRecord.vue'], resolve)
        }
      ]
    }
  ]
})
router.beforeEach((to, from, next) => {
    let tokens = Cookie.get('token');
    if (tokens) {
      next();
    } else {
      to.path === '/login' ? next() : next('/login')
    }
}); 