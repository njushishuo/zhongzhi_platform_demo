<template>

  <v-container fluid>
    <v-flex  xs10 offset-xs1 >
      <!--APP basic info-->
      <v-container >
        <span>应用信息</span>
      </v-container>
      <v-divider></v-divider>
      <v-container>
        <v-layout row wrap>
          <v-flex xs4 offset-xs1>
            <span  class="grey--text">应用名称：</span>
            <span>{{this.appVo.name}}</span>
          </v-flex>
          <v-flex xs4>
            <span  class="grey--text">应用类型：</span>
            <span>{{this.appVo.type}}</span>
          </v-flex>
        </v-layout>

        <v-layout row wrap>
          <v-flex xs4 offset-xs1>
            <span  class="grey--text">部门名称：</span>
            <span  >{{this.appVo.deptName}}</span>
          </v-flex>
          <v-flex xs4>
            <span  class="grey--text">部门编号： </span>
            <span  >{{this.appVo.deptCode}}</span>
          </v-flex>
        </v-layout>

        <v-layout row wrap>
          <v-flex xs10 offset-xs1>
            <span  class="grey--text">应用描述：</span>
            <span>{{this.appVo.description}}</span>
          </v-flex>
        </v-layout>

      </v-container>

      <!--App resource info-->
      <v-container >
          <v-layout row wrap>
            <v-flex xs12>
              <span>资源信息</span>
              <a class="right btn--flat" @click="jumpToApplyPage">追加资源</a>
            </v-flex>
          </v-layout>
      </v-container>
      <v-divider></v-divider>
      <v-container>
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
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container>
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
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container>
        <v-container >
          <span>API资源</span>
        </v-container>
        <v-data-table v-bind:headers="apiHeaders" v-bind:items="apiItems"  hide-actions class="elevation-1 text-xs-left ">

          <template slot="items" slot-scope="props" class="body-2">
            <tr>
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.apiLevel}}</td>
              <td class="text-xs-left">{{ props.item.deptName}}</td>
            </tr>
          </template>
        </v-data-table>
      </v-container>
    </v-flex>
  </v-container>

</template>


<script>
  import AppService from '@/services/appService'
  export default {
      name: "AppDetail",
      data(){
        return{
          appId:this.$route.params.app_id,
          appVo:{
            name:"",
            type:"",
            description:"",
            deptName:"",
            deptCode:"",
            resourceInfo: {
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
            { text: "发布部门", value: "",sortable: false,}
          ],
          cmptItems:[],

          dataHeaders: [
            { text: "资源名称", left: true, value: "name",sortable: false, },
            { text: "数据类型", value: "type" ,sortable: false,},
            { text: "更新周期", value: "deptName" ,sortable: false,},
            { text: "事权单位", value: "deptCode",sortable: false,},
            { text: "发布部门", value: "",sortable: false,}
          ],
          dataItems:[],

          apiHeaders: [
            { text: "服务名称", left: true, value: "name",sortable: false, },
            { text: "服务分级", value: "type" ,sortable: false,},
            { text: "发布部门", value: "",sortable: false,}
          ],
          apiItems:[],
        }
      },

      methods:{
        fetchData(){
          AppService.getAppDetail(this.appId).then((res) => {
            this.appVo = res.data;
            if(this.appVo.resourceInfo != null){
              this.cmptItems = this.appVo.resourceInfo.resrcCmptList;
              this.dataItems = this.appVo.resourceInfo.resrcDataList;
              this.apiItems  = this.appVo.resourceInfo.resrcApiList;
            }
            console.log("appvo is " +appVo.name)
            console.log(appVo.name)
          }).catch((err) => {
            // console.log(err)
            let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
          })
        },

        jumpToApplyPage(){
          this.$router.push({name:'IsvAppResourceApply', params:{app_id:this.appId}})
        }
      },

      created(){
        this.fetchData()
      }
  }
</script>
