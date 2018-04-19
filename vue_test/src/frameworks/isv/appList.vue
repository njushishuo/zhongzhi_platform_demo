<template>
  <v-container fluid>
    <v-flex xs10 offset-xs1 >
      <v-card>
        <v-card-title>
          应用总数： {{items.length}}
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
              <tr  @click = "showAppDetail(props.item.id)">
                <td class="text-xs-left">{{ props.item.name }}</td>
                <td class="text-xs-left">{{ props.item.type}}</td>
                <td class="text-xs-left">{{ props.item.deptName}}</td>
                <td class="text-xs-left">{{ props.item.deptCode}}</td>
                <td class="text-xs-left">
                  <v-btn fab small dark class="teal" @click.native="edit(props.item)">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn fab small class="cyan" @click.native="remove(props.item)">
                    <v-icon>delete</v-icon>
                  </v-btn>
                </td>
              </tr>
            </template>
        </v-data-table>
      </v-card>
    </v-flex>

    <confirm-dialog :dialog="dialog" :dialogTitle="dialogTitle" :dialogText="dialogText" @onConfirm="onConfirm" @onCancel="onCancel" ></confirm-dialog>
  </v-container>
</template>
<script>
  import ConfirmDialog from "@/components/ConfirmDialog.vue";
  import AppService from '@/services/appService'

  export default {
    name:'appList',
    components: {
      ConfirmDialog
    },
    data () {
      return {
        dialog: false,
        dialogTitle: "App Delete Dialog",
        dialogText: "是否要删除该应用?",

        headers: [
          { text: "应用名称", left: true, value: "name",sortable: false, },
          { text: "应用类别", value: "type" ,sortable: false,},
          { text: "部门名称", value: "deptName" ,sortable: false,},
          { text: "部门编号", value: "deptCode",sortable: false,},
          { text: "操作", value: "",sortable: false,}
        ],
        items:[],

        appId: "",
      };
    },
    methods: {
      print () {
        window.print();
      },
      edit (item) {

      },
      add () {
        // this.$router.push("NewProduct");
      },
      remove (item) {
        this.appId = item.id;
        this.dialog = true;
      },
      onConfirm () {

        this.dialog = false;
      },
      onCancel () {
        this.productId = "";
        this.dialog = false;
      },

      loadData(){
        let userId = this.$cookie.get('userId')
        AppService.getMyApps(userId).then((res) => {
          this.items = res.data;
          console.log(this.items)
        }).catch((err) => {
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
          console.log(errMsg)
        })
      },

      showAppDetail(appId){
         this.$router.push({name:'IsvAppDetail',params:{ app_id :appId}})
      }
    },


    created () {
      this.loadData()
    }
  };
</script>
