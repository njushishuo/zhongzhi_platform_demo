<template>
  <v-app>
    <v-navigation-drawer class="blue lighten-5" width="250"  light fixed :mini-variant.sync="mini" v-model="drawer" app>
      <!-- mini-variant.sync="true" -->
      <v-list class="pa-0">
        <v-list-tile avatar tag="div">
          <v-list-tile-avatar>
            <img src="../../assets/img/avatar0.png">
          </v-list-tile-avatar>
          <v-list-tile-content>
            <v-list-tile-title>{{username}}</v-list-tile-title>
            <v-list-tile-title>{{role}}</v-list-tile-title>
          </v-list-tile-content>
          <v-spacer></v-spacer>
          <v-list-tile-action style="min-width:30px;">
            <v-menu bottom right offset-y origin="bottom right" transition="v-slide-y-transition">
              <v-btn icon light slot="activator">
                <v-icon>more_vert</v-icon>
              </v-btn>
              <v-list>
                <v-list-tile v-for="item in userMenus" :key="item.title" value="true" :to="item.link" router>
                  <v-list-tile-title v-text="item.title"></v-list-tile-title>
                </v-list-tile>
              </v-list>
            </v-menu>

          </v-list-tile-action >
          <v-list-tile-action style="min-width:30px;">
            <v-btn icon @click.native.stop="mini = !mini">
              <v-icon>chevron_left</v-icon>
            </v-btn>
          </v-list-tile-action>
        </v-list-tile>
      </v-list>

      <v-list>
        <v-list-group
          v-model="item.active"
          v-for="item in items"
          :key="item.title"
          :prepend-icon="item.action"
          no-action
        >
          <v-list-tile slot="activator">
            <v-list-tile-content>
              <v-list-tile-title>{{ item.title }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile v-for="subItem in item.items" :key="subItem.title" :to="subItem.link" >
            <v-list-tile-content>
              <v-list-tile-title>{{ subItem.title }}</v-list-tile-title>
            </v-list-tile-content>
            <v-list-tile-action>
              <v-icon>{{ subItem.action }}</v-icon>
            </v-list-tile-action>
          </v-list-tile>
        </v-list-group>
      </v-list>


    </v-navigation-drawer>
    <v-toolbar app="">
      <v-toolbar-side-icon @click.native.stop="drawer = !drawer" light></v-toolbar-side-icon>
      <v-spacer></v-spacer>
      <div class="text-xs-center pr-3">
        <v-badge left="">
          <span slot="badge">6</span>
          <v-icon large color="grey lighten-1">shopping_cart</v-icon>
        </v-badge>

        <v-badge color="red">
          <span slot="badge">!</span>
          <v-icon large color="grey">mail</v-icon>
        </v-badge>
      </div>
    </v-toolbar>
    <v-content>
      <v-container fluid fill-height>
        <v-layout>
          <v-flex row="">
            <router-view></router-view>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
    <canvas id="canvas"></canvas>
    <v-footer :inset="true" style="justify-content:center; text-align: center" app>
      <span >&copy; Vue-CRM 2018</span>
    </v-footer>
  </v-app>

</template>

<script>
  export default {
    name: "AuditorFrame",
    data () {
      return {
        username: this.$cookie.get('username'),
        mini: false,
        drawer: true,
        items: [
          {
            action: 'shopping_cart',
            title: '工单管理',
            items: [
              { title: '待审核' ,link:{name:'UnprocessedOrderList'}},
              { title: '已处理' ,link:{name:'ProcessedOrderList'}}
            ]
          },
        ],
        userMenus: [
          {
            icon: "bubble_chart",
            title: "Logout",
            link: "/login"
          }
        ],
        menuItem: ""
      };
    },
    computed: {
      role:function(){
        let role = this.$cookie.get('userRole')
        console.log('role is '+role)
        if(role === 'isv'){
          return '开发者'
        }else if(role === 'cmpt_conductor'){
          return '计算审核员'
        }else if(role === 'data_conductor'){
          return '数据审核员'
        }else{
          return ""
        }
      },

      activeMenuItem () {
        return this.menuItem;
      }
    },
    methods: {
      clickMenu (item) {
        console.log(this.$cookie.get("user"));
        this.menuItem = item.title;
        this.$router.push({
          name: item.link
        });
      },
    },
  }
</script>

<style scoped>

</style>
