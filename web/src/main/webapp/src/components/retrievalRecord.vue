<template>
    <div>
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
                                <el-button slot="append" icon="el-icon-setting" type='info' >高级搜索</el-button>
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
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="70" v-show='scope.row.state=== "正在提取"'></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="50" v-show='scope.row.state=== "命令已下发"'></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="25" v-show='scope.row.state=== "记录生成"'></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="100" v-show='scope.row.state=== "提取成功"' status="success"></el-progress>
                        <el-progress :text-inside="true" :stroke-width="18" :percentage="100" v-show='scope.row.state=== "提取失败"' status="exception"></el-progress>
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
import {Card, Table, TableColumn, Pagination, Dialog, Select, Option, Progress} from 'element-ui'
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
      elProgress: Progress
  },
}
</script>
<style>

</style>
