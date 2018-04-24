// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import {router} from './router'
import 'element-ui/lib/theme-chalk/index.css'
import {Row, Col, Button, Input, Message, ButtonGroup} from 'element-ui'
import {post, get, deleteFn, put} from './api/http'; // 引入axios 封装的方法
import Cookie from 'vue-cookie'
Vue.use(Cookie);
Vue.config.productionTip = false
Vue.prototype.$post = post;
Vue.prototype.$put = put;
Vue.prototype.$delete = deleteFn;
Vue.prototype.$get = get;
Vue.component(Row.name, Row)
Vue.component(Col.name, Col)
Vue.component(Button.name, Button)
Vue.component(Input.name, Input)
Vue.component(ButtonGroup.name, ButtonGroup)
Vue.prototype.$message = Message
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
