<template>
  <v-container fluid>
    <v-flex xs10 offset-xs1 >
      <v-card>
        <v-card-title>
          工单总数： {{items.length}}
          <v-spacer></v-spacer>

          <v-btn class="brown lighten-1" fab small @click.native="loadData()">
            <v-icon>refresh</v-icon>
          </v-btn>
          &nbsp;
          <v-btn class="light-green darken-2" fab small @click.native="print()">
            <v-icon>print</v-icon>
          </v-btn>
          &nbsp;
          <v-btn class="deep-orange darken-3" fab small dark @click.native="add">
            <v-icon>add</v-icon>
          </v-btn>
        </v-card-title>

        <v-data-table  v-bind:headers="headers" v-bind:items="items"  hide-actions class=" red elevation-1 text-xs-left ">

          <template slot="items" slot-scope="props" class="body-2">
            <tr  @click = "showOrderDetail(props.item.id)">
              <td class="text-xs-left">{{ props.item.id }}</td>
              <td class="text-xs-left">{{ props.item.appName }}</td>
              <td class="text-xs-left">{{ props.item.userName }}</td>
              <td class="text-xs-left">{{ props.item.deptName}}</td>
              <td class="text-xs-left">{{ props.item.createTime}}</td>
            </tr>
          </template>
        </v-data-table>
      </v-card>
    </v-flex>
  </v-container>
</template>

<script>
  import WorkOrderService from '@/services/orderService'
  export default {
      name: "UnprocessedOrderList",
      data () {
        return {
          headers: [
            { text: "工单号", left: true,sortable: false, },
            { text: "应用名称", left: true, sortable: false, },
            { text: "申请人", left: true,sortable: false,},
            { text: "申请部门",left: true, sortable: false,},
            { text: "申请时间",left: true, sortable: false,},
          ],
          items:[],
        };
      },
      methods: {
        print () {
          window.print();
        },

        loadUnprocessedData(){
          var userId = this.$cookie.get('userId')
          var status = "wait_review"
          WorkOrderService.auditorGetMyOrders(userId,status).then((res) => {
            this.items1 = res.data;
            console.log(this.items1)
          }).catch((err) => {
            let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
            console.log(errMsg)
          })
        },
        showOrderDetail(orderId){
          this.$router.push({name:'AuditorOrderReview',params:{ order_id :orderId}})
        }
      },
      created () {
        this.loadUnprocessedData();
      }
    }
</script>

<style scoped>

</style>
