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
                            <el-button-group >
                                <el-button type="primary" round  size='small'>命令已下发</el-button>
                                <el-button type="primary" size='small'>正在提取</el-button>
                                <el-button type="primary"  size='small'>记录生成</el-button>
                                <el-button  type='warning' size='small'>全部记录</el-button>
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
                >
                <el-table-column
                prop="recordName"
                align='center'
                label="提取人"
                >
                </el-table-column>
                <el-table-column
                prop="recordAccount"
                align='center'
                label="提取账号"
               >
                </el-table-column>
                <el-table-column
                prop="recordDate"
                align='center'
                label="提取时间"
                sortable
                >
                </el-table-column>
                </el-table-column>
                <el-table-column
                prop="terminalNum"
                sortable
                label="终端设备编号"
                align='center'
                >
                </el-table-column>
                <el-table-column
                label="状态"
                prop="state"
                align='center'
                >
                    <template slot-scope="scope">
                        <span>{{scope.row.state}}</span>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="70" v-if='scope.row.state=== "正在提取"'></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="50" v-if='scope.row.state=== "命令已下发"'></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="25" v-if='scope.row.state=== "记录生成"'></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="100" v-if='scope.row.state=== "提取成功"' status="success"></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="100" v-if='scope.row.state=== "提取失败"' status="exception"></el-progress>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="remark"
                    align='center'
                    label="描述"
                >
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
import {Card, Table, TableColumn, Pagination, Dialog, Select, Option, Progress, Form, FormItem, DatePicker} from 'element-ui'
export default {
  name: 'retrievalRecord',
  data () {
      return {
          tableData4: [{
              recordName: 'ctf',
              recordAccount: 'xutianshun',
              recordDate: '2018-04-18 03:51:54',
              terminalNum: '1319398b481853285b93d06d836ab05f',
              remark: '',
              state: '正在提取'
          },
          {
              recordName: 'ctf',
              recordAccount: 'xutianshun',
              recordDate: '2018-04-18 03:51:54',
              terminalNum: '1319398b481853285b93d06d836ab05f',
              remark: '',
              state: '命令已下发'
          },
          {
              recordName: 'ctf',
              recordAccount: 'xutianshun',
              recordDate: '2018-04-18 03:51:54',
              terminalNum: '1319398b481853285b93d06d836ab05f',
              remark: '',
              state: '记录生成'
          },
          {
              recordName: 'ctf',
              recordAccount: 'xutianshun',
              recordDate: '2018-04-18 03:51:54',
              terminalNum: '1319398b481853285b93d06d836ab05f',
              remark: '',
              state:'提取成功'
          },
          {
              recordName: 'ctf',
              recordAccount: 'xutianshun',
              recordDate: '2018-04-18 03:51:54',
              terminalNum: '1319398b481853285b93d06d836ab05f',
              remark: '',
              state: '提取失败'
          }],
          bestSearchShow: false,
          logStateVal: '',
          logDateVal: '',
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
      elProgress: Progress,
      elForm: Form,
      elFormItem: FormItem,
      elDatePicker: DatePicker
  },
  methods: {
      logBestSearchBtn () {
          this.bestSearchShow = true;
      },
      searchClose () {
          this.bestSearchShow = false
      }
  }
}
</script>
<style>

</style>
