import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

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
