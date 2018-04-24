<template>
  <div class="loginBg">
    <el-row>
            <el-col :sm="{span:12, push:6}" :xs='24'>
                <elform :model="loginForm" :rules="rules" ref="ruleForm" label-position="left" label-width="0px"  class="login-container">
                    <h4 class="title">Log4droid</h4>
                    <elformitem prop='userName'>
                      <el-input type="text"  auto-complete="off" v-model="loginForm.userName" >
                           <template slot="prepend" >账号</template>
                      </el-input>
                    </elformitem>
                    <elformitem prop='userPwd' >
                      <el-input type="password" auto-complete="off" v-model="loginForm.userPwd">
                           <template slot="prepend">密码</template>
                      </el-input>
                    </elformitem>
                    <elformitem style="width:100%;">
                        <el-button type="primary"  class="loginbtn" @click.native.prevent="loginSubmit">登录</el-button>
                    </elformitem>
                </elform>
            </el-col>
    </el-row>
  </div>
</template>
<script>
import {Form, FormItem} from 'element-ui'
import crypto from 'crypto';
export default {
  name: 'login',
  data () {
    return {
      rules: {
                userName: [
                    { required: true, message: '请输入用户名'},
                ],
                userPwd : [
                    { required: true, message: '请输入密码'},
                ]
      },
      loginForm: {
          userName: '',
          userPwd: ''
      },
    }
  },
  components: {
    elform: Form,
    elformitem: FormItem
  },
  methods: {
    loginSubmit () {
            this.$refs.ruleForm.validate(valid => {
                if (valid) {
                   const md5 = crypto.createHash('md5');
                    md5.update(this.loginForm.userPwd);
                    this.loginForm.userPwd = md5.digest('hex');
                    this.$post('/user/login', {
                       userName: this.loginForm.userName,
                       password: this.loginForm.userPwd
                    }).then(res => {
                        if (res.data.code == 0) {
                            this.$cookie.set('token', res.data.data.token, 1);
                            this.$cookie.set('userName', this.loginForm.userName, 1);
                            this.$router.push('/')
                        }
                    }).catch(err => {
                        this.loginForm.userPwd = '';
                        this.$message.error('登录失败,请检查账号密码是否正确')
                    })
                } else {
                    this.loginForm.userPwd = '';
                    this.$message.error('请输入账号密码');
                }
            })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.loginBg{background: #4c5864;height: 100%;position: absolute;width: 100%}
.login-container {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    margin: 25% auto;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    max-width: 280px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;

}
.title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
}
.loginbtn {
        width: 40%;
        margin: 20px 0px 0px 30%
}
.loginbtn span {
  text-align: center
}
</style>
