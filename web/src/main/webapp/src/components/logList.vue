<template>
    <div>
        <el-dialog
        custom-class="traceDialog"
        fullscreen
        :visible="traceShow"
        append-to-body
        width="95%"
        :before-close='closeTrace'
        >
        <el-row slot="title" align='middle'>
            <el-col :span='6' style="float: left">
                终端实时__{{tracedata.MID}}
            </el-col>
            <el-col :span='8' :push='4'>
                <el-row align='middle'>
                    <el-col :sm='12'>
                        <el-select v-model="traceVal" placeholder="Trace级别" size='small' style="marginTop:-5px">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :sm='10'>
                            <el-switch
                            style="display: block"
                            v-model="SwitchState"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="开始"
                            inactive-text="暂停">
                            </el-switch>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <div class="dialog-body">
            {{tracedata}}
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="closeTrace" type='warning'>关闭</el-button>
        </span>
        </el-dialog>
        <!-- trace 面板 -->
         <el-dialog
        :visible="LogShow"
        append-to-body
        width="40%"
        :before-close='LogClose'
        >
        <el-row slot="title" align='middle'>
            终端提取日志(每天日志总量约800k~1200k)
        </el-row>
        <div >
            <el-form  status-icon  ref="ruleForm2" label-width="110px" class="demo-ruleForm">
            <el-form-item label="用户名：" >
                <el-input type="text"  auto-complete="off" v-model="tracedata.MID" disabled></el-input>
            </el-form-item>
            <el-form-item label="提取流量上限：(单位:Kb)">
                <el-input type="password"  auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="日志类别">
                <el-select v-model="logSortVal" placeholder="Trace级别" size='small' style="marginTop:-5px">
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
                v-model="dateVal"
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
            <el-button  type='danger' size='small' @click='LogClose'>关闭</el-button>
        </span>
        </el-dialog>
         <!-- 提取日志 面板 -->
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
            <el-form-item label="MID：" >
                <el-input type="text"  auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="型号：">
                <el-input type="text"  auto-complete="off"></el-input>
            </el-form-item>
             <el-form-item label="IMEI：">
                <el-input type="text"  auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="终端类型：">
                <el-select v-model="logSortVal" placeholder="Trace级别" size='small' style="marginTop:-5px">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="登录时间：" >
                 <el-date-picker
                v-model="dateVal"
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
          <!-- 高级搜索 面板 -->
        <el-card class="box-card">
            <el-row class="height">
                <el-col :lg='{span:16}' :sm='{span:24}' style="marginTop:5px" class="height">
                    <el-row>
                        <el-col :sm='{span:8}' :xs='{span:24}'>
                            <el-button-group >
                                <el-button type="primary" round icon="el-icon-location" size='small'>已登录</el-button>
                                <el-button type="danger" round icon="el-icon-location-outline
" size='small'>未登录</el-button>
                            </el-button-group>
                        </el-col>
                        <el-col :sm='{span:16}' :xs='{span:24}'>
                            <el-button-group >
                                <el-button type="primary" round icon="el-icon-edit" size='small'>PC端</el-button>
                                <el-button type="primary" icon="el-icon-tickets
" size='small'>平板</el-button>
                                <el-button type="primary" icon="el-icon-share" size='small'>智能穿戴设备</el-button>
                                <el-button type="primary" round icon="el-icon-mobile-phone" size='small'>移动设备</el-button>
                            </el-button-group>
                        </el-col>
                    </el-row>    
                </el-col>
                <el-col :lg='{span:8}' :sm='{span:12}' >
                    <el-row >
                        <el-col :span='24' style="marginTop:5px">
                            <el-input  placeholder='输入Mac / IMEI / UID 搜索用户' >
                                <el-button slot="append" icon="el-icon-search" id='searchBtn' type='primary'>搜索</el-button>
                                <el-button slot="append" icon="el-icon-setting" type='info' @click='bestSearchBtn'>高级搜索</el-button>
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
                prop="termtyp"
                align='center'
                label="终端类型"
                width="120">
                </el-table-column>
                <el-table-column
                prop="model"
                align='center'
                label="型号"
                width="120">
                </el-table-column>
                <el-table-column
                prop="versions"
                align='center'
                label="版本"
                sortable
                width="120">
                </el-table-column>
                <el-table-column
                prop="MID"
                label="MID"
                align='center'
                width="120">
                </el-table-column>
                <el-table-column
                prop="Mac"
                label="Mac_Address"
                align='center'
                width="200">
                </el-table-column>
                <el-table-column
                prop="IMEI"
                label="IMEI"
                align='center'
                width="300">
                </el-table-column>
                <el-table-column
                label="状态"
                align='center'
                width="180">
                    <template slot-scope="scope">
                        <el-switch
                            disabled
                            style="display: block"
                            v-model="scope.row.state"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="登录"
                            inactive-text="离线">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="date"
                    sortable
                    align='center'
                    label="最近一次登录时间"
                    width="200">
                </el-table-column>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align='center'
                    width="250">
                    <template slot-scope="scope">
                        <el-button  size="small" style="background:#F56C6C;color:white" @click="recordTrace(scope.row)">Trace</el-button>
                        <el-button type="primary" size="small" @click="OpenLogSection(scope.row)">提取日志</el-button>
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
import {Card, Table, TableColumn, Pagination, Dialog, Select, Option, Switch, Form, FormItem, DatePicker} from 'element-ui'
export default {
  name: 'logList',
  data () {
      return {
        tableData4: [{
          termtyp: 'PC',
          model: 'XXXXX',
          versions: '1.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: false,
          date: '2016-05-03'
        }, {
          termtyp: 'PC',
          model: 'XXXXX',
          versions: '7.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: true,
          date: '2016-05-03'
        }, {
          termtyp: 'PC',
          model: 'XXXXX',
          versions: '6.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: false,
          date: '2016-05-03'
        }, {
         termtyp: 'PC',
          model: 'XXXXX',
          versions: '5.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: true,
          date: '2016-05-03'
        }, {
         termtyp: 'PC',
          model: 'XXXXX',
          versions: '4.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: true,
          date: '2016-05-03'
        }, {
          termtyp: 'PC',
          model: 'XXXXX',
          versions: '3.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: true,
          date: '2016-05-03'
        }, {
          termtyp: 'PC',
          model: 'XXXXX',
          versions: '2.0',
          MID: '!1671430928',
          Mac: 200333,
          IMEI: 456110210036376,
          state: false,
          date: '2016-05-03'
        }],
        traceShow: false,
        tracedata: '',
        traceVal: 'DEBUG',
        logSortVal: 'DEBUG',
        LogShow: false,
        bestSearchShow: false,
        SwitchState: true,
        dateVal: '',
        options: [{
          value: 'FATAL',
          label: 'FATAL'
        }, {
          value: 'ALERT',
          label: 'ALERT'
        }, {
          value: 'CRITICAL',
          label: 'CRITICAL'
        }, {
          value: 'ERROR',
          label: 'ERROR'
        }, {
          value: 'WARNING',
          label: 'WARNING'
        },
        {
          value: 'NOTICE',
          label: 'NOTICE'
        },
        {
          value: 'INFO',
          label: 'INFO'
        },
        {
          value: 'DEBUG',
          label: 'DEBUG'
        }],
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
      elSwitch: Switch,
      elForm: Form,
      elFormItem: FormItem,
      elDatePicker: DatePicker
  },
  methods: {
      test () {
          alert(1111111111)
      },
      recordTrace (data) {
          this.tracedata = '';
          this.traceShow = true;
          this.tracedata = data;
      },
      OpenLogSection (data) {
          this.tracedata = '';
          this.dateVal = '';
          this.LogShow = true;
          this.tracedata = data;
      },
      closeTrace () {
          this.traceShow = false;
      },
      LogClose () {
          this.LogShow = false
      },
      bestSearchBtn () {
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
