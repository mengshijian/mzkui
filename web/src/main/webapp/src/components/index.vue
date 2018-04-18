<template>
    <div class="hello">
        <el-container>
        <el-header>
          <el-row>
              <el-col :xs="6" :sm='4' style="color:white">
                Log4droid
              </el-col>
              <el-col  :xs='{span:4, offset:14}' :sm="{span:2, offset:18}" >
                 <el-dropdown trigger="hover">
                    <span class="el-dropdown-link" style="color:white">
                      ctf<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown" >
                      <el-dropdown-item>我的消息</el-dropdown-item>
                      <el-dropdown-item>设置</el-dropdown-item>
                      <el-dropdown-item divided @click.native.prevent="logout">退出</el-dropdown-item>
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
                  :default-active="$route.name"
                  class="el-menu-vertical-demo"
                  background-color="#333c45"
                  text-color="#fff"
                  active-text-color="#ffd04b"
                  router
                  style="height:100%;width:100%"
                  >
                  <!-- <el-submenu index="loglist">
                    <template slot="title">
                      <i class="el-icon-loading"></i>
                      <span>终端管理</span>
                    </template> -->
                    <!-- <el-menu-item-group> -->
                      <el-menu-item index="loglist">
                          <i class="el-icon-document"></i>用户列表
                          </el-menu-item>
                      <el-menu-item index="record">
                           <i class="el-icon-sort"></i>提取记录
                           </el-menu-item>
                    <!-- </el-menu-item-group> -->
                  <!-- </el-submenu> -->
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
          this.$message.success('退出成功')
    }
  }
}
</script>
<style >
.box-card .el-card__body{
    padding: 10px;
    height: 100%;
}
.height {
    height:100%
}
.box-card #searchBtn {
    border-color: #dcdfe6;
    border-left: none;
    background: #409EFF;
    color:white;
}
#LogListTable {
    width: 100%;
    height: 635px;
    margin-top: 1%;
    border-radius: 5px;
}
#LogListTable .el-table__body-wrapper {
    height: 91%;
}
.page span{
    color: white
}
.page {
    margin-top: 30px;
}
.page .btn-prev {
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
}
.page .btn-next {
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
}
.traceDialog {
    border: 1px solid lightgray;
    border-radius: 10px;
    box-shadow: 0 5px 20px 0 rgba(255,255,255,.5)
}
.traceDialog .el-dialog__header {
    border-bottom: 1px solid lightgray
}
.dialog-body {
    min-height: 650px;
    overflow: auto;
    background: #1e282c;
    color: white
}
.el-header, .el-footer {
    background-color: #2c373b;
    color: white;
    text-align: center;
    line-height: 60px;
    padding: 0px
  }
.el-container,.hello,.el-main,.el-aside{
    height: 100%
}
.el-menu-item.is-active {
    background: rgb(77, 174, 165) !important;
    color: white !important;
}
.el-aside {
color: #333;
text-align: center;
overflow: hidden;
}
</style>
