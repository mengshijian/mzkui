<template>
    <div>
         <el-dialog
        :visible="bestSearchShow"
        append-to-body
        width="40%"
        :before-close='searchClose'
        >
        <el-row slot="title" align='middle'>
            高级查询
        </el-row>
        <div >
            <el-form  status-icon  ref="ruleForm2" label-width="110px" class="demo-ruleForm">
            <el-form-item label="终端设备编号：" >
                <el-input type="text"  auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="提取人：">
                <el-input type="text"  auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态：">
                <el-select v-model="logStateVal" placeholder="状态类别" class="stateSelect">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="日期：" >
                 <el-date-picker
                v-model="logDateVal"
                style="width:100%"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
                </el-date-picker>
            </el-form-item>
            </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button  type='success' size='small'>确定</el-button>
            <el-button  type='danger' size='small' @click='searchClose'>关闭</el-button>
        </span>
        </el-dialog>
        <el-card class="box-card">
            <el-row class="height">
                <el-col :lg='{span:16}' :sm='{span:24}' style="marginTop:5px" class="height">
                            <el-button-group @click.native='recordCardBtnGruop($event)' class="btns">
                                <el-button type="primary" round  size='small'>命令已下发</el-button>
                                <el-button type="primary" size='small'>正在提取</el-button>
                                <el-button type="primary"  size='small'>记录生成</el-button>
                                <!-- <el-button  type='warning' size='small'>全部记录</el-button> -->
                                <el-button type="success" round  size='small'>提取成功</el-button>
                                <el-button type="danger" round  size='small'>提取失败</el-button>
                            </el-button-group>  
                </el-col>
                <el-col :lg='{span:8}' :sm='{span:12}' style="float:right">
                    <el-row >
                        <el-col :span='24' style="marginTop:5px">
                            <el-input  placeholder='输入设备编号 / 提取人 搜索记录' >
                                <el-button slot="append" icon="el-icon-search" id='searchBtn' type='primary'>搜索</el-button>
                                <el-button slot="append" icon="el-icon-setting" type='info' @click='logBestSearchBtn'>高级搜索</el-button>
                            </el-input>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-card>
        <el-row>
            <el-col :span='24'>
                <el-table
                :data="tableData4"
                border
                stripe
                id="LogListTable"
                size="small"
                :height='tableHeight + "px"'
                >
                <el-table-column
                prop="recordName"
                align='center'
                label="提取人"
                >
                </el-table-column>
                <el-table-column
                prop="userId"
                align='center'
                label="提取账号"
               >
                </el-table-column>
                <el-table-column
                prop="createTime"
                align='center'
                label="提取时间"
                sortable
                >
                </el-table-column>
                </el-table-column>
                <el-table-column
                prop="deviceId"
                sortable
                label="终端设备编号"
                align='center'
                >
                </el-table-column>
                <el-table-column
                label="状态"
                width='350'
                align='center'
                >
                    <template slot-scope="scope">
                        <!-- <span v-stepText :url='scope.row.status || ""' :state='scope.row.status'>{{scope.row.status}}</span> -->
                        <span v-if="scope.row.status === -1">提取失败</span>
                        <span v-if="scope.row.status === 0">记录生成</span>
                        <span v-if="scope.row.status === 1">命令下发</span>
                        <span v-if="scope.row.status === 2">提取记录</span>
                        <a :href='scope.row.status' v-if="scope.row.status === 3" download title="点击下载记录">提取成功</a>
                        <span v-if="scope.row.status === 4">WEB参数错误</span>
                        <el-steps v-if='scope.row.status < 4 && scope.row.status >= 0' :active='scope.row.status' finish-status='finish' process-status='success' align-center >
                            <el-step title="记录生成" ></el-step>
                            <el-step title="命令下发" ></el-step>
                            <el-step title="提取记录" ></el-step>
                            <el-step title="提取成功" ></el-step>
                        </el-steps>
                         <el-steps :active='scope.row.status'  align-center v-if='scope.row.status === -1'>
                            <el-step title="记录生成" ></el-step>
                            <el-step title="命令下发" ></el-step>
                            <el-step title="提取记录" ></el-step>
                            <el-step title="执行失败" status="error"></el-step>
                        </el-steps>
                        <el-steps :active='0'  align-center v-if='scope.row.status === 4'>
                            <el-step title="WEB参数错误" status="error"></el-step>
                            <el-step title="记录生成" ></el-step>
                            <el-step title="命令下发" ></el-step>
                            <el-step title="提取记录" ></el-step>
                        </el-steps>
                    </template>
                </el-table-column>
            </el-table>
            </el-col>
        </el-row>
        <el-row>
            <el-col class="page" >
                 <el-pagination
                    style="float:right"
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="100"
                    layout="total, sizes, prev, pager, next, jumper"
                :total="400">
                </el-pagination>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import {Card, Table, TableColumn, Pagination, Dialog, Select, Option, Form, FormItem, DatePicker, Steps, Step} from 'element-ui'
export default {
  name: 'retrievalRecord',
  data () {
      return {
          tableData4: [],
          bestSearchShow: false,
          logStateVal: '',
          logDateVal: '',
          tableHeight: '',
          options: [{
          value: '记录生成',
          label: '记录生成'
          },
          {
          value: '命令已下发',
          label: '命令已下发'
          },
          {
          value: '终端已接受命令',
          label: '终端已接受命令'
          },
          {
          value: '执行成功',
          label: '执行成功'
          },
          {
          value: 'WEB参数错误',
          label: 'WEB参数错误'
          },
          {
          value: '执行失败',
          label: '执行失败'
          }]
      }
  },
  components: {
      elCard: Card,
      elTable: Table,
      elTableColumn: TableColumn,
      elPagination: Pagination,
      elDialog: Dialog,
      elSelect: Select,
      elOption: Option,
      elForm: Form,
      elFormItem: FormItem,
      elDatePicker: DatePicker,
      elSteps: Steps,
      elStep: Step
  },
  methods: {
      logBestSearchBtn () {
          this.bestSearchShow = true;
      },
      searchClose () {
          this.bestSearchShow = false
      },
      tableResize () {
           this.tableHeight = document.getElementsByClassName('el-main')[0].clientHeight - document.getElementsByClassName('box-card')[0].clientHeight - 115;
            window.addEventListener('resize', () => {
                this.tableHeight = document.getElementsByClassName('el-main')[0].clientHeight - document.getElementsByClassName('box-card')[0].clientHeight - 115;
            })
      },
      getTableData () {
          this.$post('/extractLog/list').then(res => {
              this.tableData4 = res.data.data
          })
      },
      recordCardBtnGruop(el) {
          let target = el.target; // 获取点击源
          if (target.tagName === 'BUTTON') {
               target = target.lastChild;
          }
          if(!target.classList.contains('click_active')) {
                 target.classList.add('click_active'); // 未选中添加选中状态并获取特定条件的数据
                 let btns = document.getElementsByClassName('btns')[0].getElementsByClassName('el-button') // 获取所有兄弟节点
                 for (var i = 0, y = btns.length; i < y; i++) {
                     if (btns[i].lastChild.innerText !== target.innerText) {
                         btns[i].lastChild.classList.remove('click_active') // 删除其他兄弟节点的选中状态
                     }
                 }
          }else {
               target.classList.remove('click_active'); // 节点源已选中的情况下删除选中状态，重置数据
          }
         
      } // 筛选按钮
  },
  mounted () {
     this.tableResize();
     this.getTableData();
  },
//   directives: {
//       stepText: {
//           inserted: function (el, binding, vnode) {
//              switch (el.getAttribute('state')) {
//                  case '-1':
//                      el.innerText = '执行失败'
//                      break;
//                 case '0':
//                      el.innerText = '记录生成'
//                      break;
//                 case '1':
//                      el.innerText = '命令已下发'
//                 break;
//                 case '2':
//                      el.innerText = '终端已接收命令'
//                 break;
//                 case '3':
//                      let url = el.getAttribute('url');
//                      el.innerHTML = `<a href="${url}" download title='点击下载记录'>执行成功</a>`
//                 break;
//                 case '4':
//                      el.innerText = 'WEB参数错误'
//                 break;               
//              }
//           }
//       }
//   }
}
</script>
<style>
/* .click_active{
    background: red
} */
</style>
