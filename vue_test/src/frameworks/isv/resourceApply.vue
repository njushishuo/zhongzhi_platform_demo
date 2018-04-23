<template>
  <v-container fluid>
    <v-flex  xs10 offset-xs1 >

      <v-container>
        <span>申请资源</span>
      </v-container>

      <v-divider></v-divider>

      <v-container>
        <v-container >
          <span>计算资源</span>
        </v-container>
        <v-data-table v-model="cmptSelected" v-bind:headers="cmptHeaders" v-bind:items="resourceInfo.resrcCmptList"  hide-actions class="elevation-1 text-xs-left ">
          <template slot="headers" scope="props">
            <tr>
              <th v-for="header in props.headers" :key="header.text">
                {{ header.text }}
              </th>
              <th>
                <v-checkbox
                  primary
                  hide-details
                  @click.native="toggleAllCmpt()"
                  :input-value="props.all"
                  :indeterminate="props.indeterminate"
                ></v-checkbox>
              </th>
            </tr>
          </template>

          <template slot="items" slot-scope="props" class="body-2">
            <tr :active="props.selected" @click="props.selected = !props.selected">
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.cmptType}}</td>
              <td class="text-xs-left">{{ props.item.count}}</td>
              <td class="text-xs-left">{{ props.item.config}}</td>
              <td class="text-xs-left">{{ props.item.department.name}}</td>
              <td>
                <v-checkbox primary hide-details :input-value="props.selected"></v-checkbox>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container>
        <v-container >
          <span>数据资源</span>
        </v-container>
        <v-data-table v-model="dataSelected" v-bind:headers="dataHeaders" v-bind:items="resourceInfo.resrcDataList"  hide-actions class="elevation-1 text-xs-left ">

          <template slot="headers" scope="props">
            <tr>
              <th v-for="header in props.headers" :key="header.text">
                {{ header.text }}
              </th>
              <th>
                <v-checkbox
                  primary
                  hide-details
                  @click.native="toggleAllData()"
                  :input-value="props.all"
                  :indeterminate="props.indeterminate"
                ></v-checkbox>
              </th>
            </tr>
          </template>

          <template slot="items" slot-scope="props" class="body-2">
            <tr :active="props.selected" @click="props.selected = !props.selected">
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.dataType}}</td>
              <td class="text-xs-left">{{ props.item.updateCycle}}</td>
              <td class="text-xs-left">{{ props.item.sqdwName}}</td>
              <td class="text-xs-left">{{ props.item.department.name}}</td>
              <td>
                <v-checkbox primary hide-details :input-value="props.selected"></v-checkbox>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container>
        <v-container >
          <span>API资源</span>
        </v-container>
        <v-data-table  v-model="apiSelected" v-bind:headers="apiHeaders" v-bind:items="resourceInfo.resrcApiList"  hide-actions class="elevation-1 text-xs-left ">

          <template slot="headers" scope="props">
            <tr>
              <th v-for="header in props.headers" :key="header.text">
                {{ header.text }}
              </th>
              <th>
                <v-checkbox
                  primary
                  hide-details
                  @click.native="toggleAllApi()"
                  :input-value="props.all"
                  :indeterminate="props.indeterminate"
                ></v-checkbox>
              </th>
            </tr>
          </template>

          <template slot="items" slot-scope="props" class="body-2">
            <tr :active="props.selected" @click="props.selected = !props.selected">
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-left">{{ props.item.apiLevel}}</td>
              <td class="text-xs-left">{{ props.item.department.name}}</td>
              <td>
                <v-checkbox primary hide-details :input-value="props.selected"></v-checkbox>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-container>

      <v-container>
        <span>备注</span>
        <v-text-field
          name="input-1-3"
          single-line
        ></v-text-field>
      </v-container>

      <v-container>
        <v-layout row wrap align-center>
          <v-flex class="text-xs-center">
            <v-btn  color="info" @click = "apply">
              提交
            </v-btn>
          </v-flex>
        </v-layout>
      </v-container>


    </v-flex>
  </v-container>
</template>

<script>
  import ResourceService from '@/services/resourceService'
  import OrderService from '@/services/orderService'
  export default {
    name: "ResourceApply",
    data(){
      return{
        orderId:this.$route.params.app_id,
        resourceDetail: {
          resrcCmptList : [],
          resrcDataList: [],
          resrcApiList:[]
        },


        cmptHeaders: [
          { text: "资源名称", left: true, sortable: false, },
          { text: "资源类型", sortable: false,},
          { text: "数量", sortable: false,},
          { text: "配置", sortable: false,},
          { text: "发布部门", sortable: false,}
        ],
        cmptItems:[],
        cmptSelected:[],

        dataHeaders: [
          { text: "资源名称", left: true, value: "name",sortable: false, },
          { text: "数据类型", value: "type" ,sortable: false,},
          { text: "更新周期", value: "deptName" ,sortable: false,},
          { text: "事权单位", value: "deptCode",sortable: false,},
          { text: "发布部门", value: "",sortable: false,}
        ],
        dataItems:[],
        dataSelected:[],

        apiHeaders: [
          { text: "服务名称", left: true, value: "name",sortable: false, },
          { text: "服务分级", value: "type" ,sortable: false,},
          { text: "发布部门", value: "",sortable: false,}
        ],
        apiItems:[],
        apiSelected:[],
      }
    },

    methods:{
      fetchData(){
        ResourceService.getResourceAppCanApply(this.orderId).then((res) => {
          this.resourceInfo = res.data;
          console.log("resourceDetail: ")
          console.log(this.resourceDetail)
        }).catch((err) => {
          // console.log(err)
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
        })
      },
      toggleAllCmpt(){
        if (this.cmptSelected.length != 0) this.cmptSelected = []
        else this.cmptSelected = this.resourceDetail.resrcCmptList.slice()
      },
      toggleAllData(){
        if (this.dataSelected.length != 0) this.dataSelected = []
        else this.dataSelected = this.resourceDetail.resrcDataList.slice()
      },
      toggleAllApi(){
        if (this.apiSelected.length != 0) this.apiSelected = []
        else this.apiSelected = this.resourceDetail.resrcApiList.slice()
      },

      apply(){
        console.log('orderId:'+this.orderId);
        var applyInfo = {
          orderId:"",
          userId:"",
          cmptList:[],
          dataList:[],
          apiList:[]
        };
        applyInfo.appId = this.orderId;
        applyInfo.userId = this.$cookie.get("userId");
        applyInfo.cmptList = this.cmptSelected;
        applyInfo.dataList = this.dataSelected;
        applyInfo.apiList = this.apiSelected;

        OrderService.createOrder(applyInfo).then((res) => {
          this.$router.push({name:'IsvAppDetail',params:{app_id:this.orderId}})
        }).catch((err) => {
          // console.log(err)
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
        })
      }

    },

    created(){
      this.fetchData()
    }
  }
</script>

