<template>
    <div class="hello">
        <el-container>
        <el-header>
          <el-row>
              <el-col :xs="6" :sm='4' >
                  <img src="../../static/Log4Droid__logo.png"  style="height:40px;float:left;margin:10px 40px" title="Log4Droid">
              </el-col>
              <el-col  :xs='{span:4, offset:14}' :sm="{span:2, offset:18}" >
                 <el-dropdown trigger="hover">
                    <span class="el-dropdown-link" style="color:white">
                      ctf<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown" >
                      <!-- <el-dropdown-item>我的消息</el-dropdown-item>
                      <el-dropdown-item>设置</el-dropdown-item> -->
                      <el-dropdown-item  @click.native.prevent="logout">退出</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
              </el-col>
          </el-row>
        </el-header>
        <!-- header -->
        <el-container>
          <el-row class="hello" style="width:100%" type='flex'>
            <el-col class="hello" :lg='3' :sm="2" :xs="4">
              <el-aside width='100%'>
                <el-menu
                  mode='vertical'
                  :default-active="$route.name"
                  class="el-menu-vertical-demo"
                  background-color="#333c45"
                  text-color="#fff"
                  active-text-color="#ffd04b"
                  router
                  style="height:100%;width:100%"
                  :collapse='isCollapse'
                  >
                    <el-submenu index="loglist">
                        <template slot="title">
                             <i class="el-icon-menu"></i>
                            <span style="color:white">日志系统</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="loglist">
                                <i class="el-icon-document"></i>用户列表
                                </el-menu-item>
                            <el-menu-item index="record">
                                <i class="el-icon-sort"></i>提取记录
                           </el-menu-item>
                        </el-menu-item-group>
                    </el-submenu >
                </el-menu>
              </el-aside>
              <!-- 侧边栏 -->
            </el-col >
            <el-col class="hello" :lg='21' :sm="22" :xs='20'>
              <el-main style="background:#4c5864;width:100%">
                <router-view></router-view>
              </el-main>
            </el-col>
          </el-row>
        </el-container>
      </el-container>
    </div>
</template>
<script>
import {Container, Header, Main, Dropdown, DropdownMenu, DropdownItem, Aside, Submenu, MenuItem, Menu,  MenuItemGroup} from 'element-ui'
export default {
  name: 'index',
  data () {
      return {
          isCollapse: false
      }
  },
  components: {
      elContainer: Container,
      elHeader: Header,
      elMain:Main,
      elDropdown: Dropdown,
      elDropdownMenu: DropdownMenu,
      elDropdownItem: DropdownItem,
      elAside: Aside,
      elMenu: Menu,
      elSubmenu: Submenu,
      elMenuItem: MenuItem,
      elMenuItemGroup: MenuItemGroup
  },
  methods: {
      logout () {
          this.$cookie.delete('token');
          this.$cookie.delete('userName');
          this.$router.push('/login');
          this.$message.success('退出成功');
    }
  },
  mounted () {
      var windowWidth = window.innerWidth;
      var windowHeight = window.innerHeight;
      windowWidth < 1200 || windowHeight < 760 ? this.isCollapse = true : this.isCollapse = false
      window.addEventListener('resize', () => {
          windowWidth = window.innerWidth;
          windowHeight = window.innerHeight;
          if (windowWidth < 1200 || windowHeight < 760) {
              this.isCollapse = true
          } else{
              this.isCollapse = false
          }
      }, true)
  }
}
</script>
<style >
@import '../../static/index.css';
</style>
