<template>
  <v-container fluid>
    <v-flex  xs10 offset-xs1 >
      <!--APP basic info-->
      <v-container >
        <span>工单基本信息</span>
      </v-container>
      <v-divider></v-divider>
      <v-container>
        <v-layout row wrap>
          <v-flex xs4 offset-xs1>
            <span  class="grey--text">应用名称：</span>
            <span>{{this.orderVo.appName}}</span>
          </v-flex>
          <v-flex xs4>
            <span  class="grey--text">创建时间：</span>
            <span  >{{this.orderVo.createTime}}</span>
          </v-flex>
        </v-layout>

        <v-layout row wrap>
          <v-flex xs4 offset-xs1>
            <span  class="grey--text">部门名称：</span>
            <span  >{{this.orderVo.deptName}}</span>
          </v-flex>
          <v-flex xs4>
            <span  class="grey--text">审核时间： </span>
            <span  >{{this.orderVo.reviewTime}}</span>
          </v-flex>
        </v-layout>

        <v-layout row wrap>
          <v-flex xs4 offset-xs1>
            <span  class="grey--text">工单状态： </span>
            <span  >{{this.orderVo.status}}</span>
          </v-flex>
          <v-flex xs4>
            <span  class="grey--text">审核结果： </span>
            <span  >{{this.orderVo.reviewStatus}}</span>
          </v-flex>
        </v-layout>

        <v-layout row wrap>
          <v-flex xs4 offset-xs1>
            <span  class="grey--text">申请人：</span>
            <span  >{{this.orderVo.userName}}</span>
          </v-flex>
        </v-layout>

      </v-container>

      <!--App resource info-->
      <v-container >
        <v-layout row wrap>
          <v-flex xs12>
            <span>资源申请信息</span>
          </v-flex>
        </v-layout>
      </v-container>
      <v-divider></v-divider>

      <v-container v-if="showCmpt">
        <v-container >
          <span>计算资源</span>
        </v-container>
        <v-data-table v-bind:headers="cmptHeaders" v-bind:items="cmptItems"  hide-actions class="elevation-1 text-xs-left ">
          <template slot="headers" scope="props">
            <tr>
              <th v-for="header in props.headers" :key="header.text">
                {{ header.text }}
              </th>
            </tr>
          </template>

          <template slot="items" slot-scope="props" class="body-2">
            <tr>
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.cmptType}}</td>
              <td class="text-xs-left">{{ props.item.count}}</td>
              <td class="text-xs-left">{{ props.item.config}}</td>
              <td class="text-xs-left">{{ props.item.deptName}}</td>
              <td><v-switch v-model="props.item.approved" ></v-switch></td>
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container v-if="showData">
        <v-container >
          <span>数据资源</span>
        </v-container>
        <v-data-table flat v-bind:headers="dataHeaders" v-bind:items="dataItems"  hide-actions class="elevation-1 text-xs-left ">

          <template slot="items" slot-scope="props" class="body-2">
            <tr>
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.dataType}}</td>
              <td class="text-xs-left">{{ props.item.updateCycle}}</td>
              <td class="text-xs-left">{{ props.item.sqdwName}}</td>
              <td><v-switch v-model="props.item.approved" ></v-switch></td>
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container v-if="showData">
        <v-container >
          <span>API资源</span>
        </v-container>
        <v-data-table v-bind:headers="apiHeaders" v-bind:items="apiItems"  hide-actions class="elevation-1 text-xs-left ">

          <template slot="items" slot-scope="props" class="body-2">
            <tr>
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.apiLevel}}</td>
              <td class="text-xs-left">{{ props.item.deptName}}</td>
              <td><v-switch v-model="props.item.approved" ></v-switch></td>
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container>
        <v-layout row wrap align-center>
          <v-flex class="text-xs-center">
            <v-btn  color="info" @click = "applyReview">
              提交
            </v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </v-flex>
  </v-container>
</template>

<script>
  import OrderService from '@/services/orderService'
  export default {
    name: "AuditorOrderReview",
    data(){
      return{
        userRole:this.$cookie.get('userRole'),
        orderId:this.$route.params.order_id,
        showCmpt: this.userRole == "cmpt_conductor",
        showData: this.userRole == "data_conductor",
        orderVo:{
          id:"",
          appName:"",
          userName:"",
          deptName:"",
          createTime:"",
          reviewTime:"",
          resourceDetail: {
            resrcCmptList : [],
            resrcDataList: [],
            resrcApiList:[]
          }
        },

        cmptHeaders: [
          { text: "资源名称", left: true, value: "name",sortable: false, },
          { text: "资源类型", value: "type" ,sortable: false,},
          { text: "数量", value: "deptName" ,sortable: false,},
          { text: "配置", value: "deptCode",sortable: false,},
          { text: "发布部门", value: "",sortable: false,},
          { text: "审核", value: "",sortable: false,},
        ],
        cmptItems:[],

        dataHeaders: [
          { text: "资源名称", left: true, value: "name",sortable: false, },
          { text: "数据类型", value: "type" ,sortable: false,},
          { text: "更新周期", value: "deptName" ,sortable: false,},
          { text: "事权单位", value: "deptCode",sortable: false,},
          { text: "审核", value: "",sortable: false,},

        ],
        dataItems:[],

        apiHeaders: [
          { text: "服务名称", left: true, value: "name",sortable: false, },
          { text: "服务分级", value: "type" ,sortable: false,},
          { text: "发布部门", value: "",sortable: false,},
          { text: "审核", value: "",sortable: false,},

        ],
        apiItems:[],


      }
    },

    methods:{
      fetchData(){
        var userId = this.$cookie.get('userId')
        OrderService.auditorGetOrderDetail(userId,this.orderId).then((res) => {
          this.orderVo = res.data;
          if(this.orderVo.resourceDetail != null){
            this.cmptItems = this.orderVo.resourceDetail.resrcCmptList;
            this.dataItems = this.orderVo.resourceDetail.resrcDataList;
            this.apiItems  = this.orderVo.resourceDetail.resrcApiList;
          }
          console.log("ordervo is ")
          console.log(this.orderVo)
        }).catch((err) => {
          // console.log(err)
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
        })
      },

      applyReview(){

        var userId = this.$cookie.get('userId');
        OrderService.review(userId,this.orderVo).then((res) => {

          this.$router.go(-1);

        }).catch((err) => {
          // console.log(err)
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
        })
      }
    },

    created(){
      this.userRole = this.$cookie.get('userRole'),
        this.orderId = this.$route.params.order_id,
        this.showCmpt = (this.userRole == "cmpt_conductor"),
        this.showData = (this.userRole == "data_conductor"),
        this.fetchData()
    }
  }
</script>

<style scoped>

</style>
