<template>
  <div>
  <v-data-table class="elevation-1" v-bind:headers="headers" v-bind:items="items"  hide-actions>

      <template slot="headers" slot-scope="props">
          <tr>
            <th v-for="header in props.headers" :key="header.text"
            :class="['column', 'subheading' , 'text-xs-left']">
              {{ header.text }}
            </th>
          </tr>
    </template>

    <template class="body-2" slot="items" slot-scope="props">
      <td class="text-xs-left body-2" v-for="(header, index) in headers" :key="index" v-if="header.value!==''">
        {{renderData(props.item, header)}}
      </td>
      <td class="text-xs-right">
        <v-btn class="teal" fab small dark @click.native="$emit('edit', props.item)">
          <v-icon>edit</v-icon>
        </v-btn>
        <v-btn class="cyan" fab small @click.native="$emit('remove', props.item)">
          <v-icon>delete</v-icon>
        </v-btn>
      </td>
    </template>
    <template slot="no-data">
     <span >
        <p class="pt-2 blue--text subheading">   <v-icon medium class="blue--text" >info</v-icon>Sorry, nothing to display here :(</p>
      </span>
    </template>
  </v-data-table>
</div>
</template>
<script>
export default {
  props: {
    headers: '',
    items: '',
  },
  data () {
    return {
    }
  },
  methods: {
    renderData: (item, header) => {
      if (header.value.includes('.')) {
        const vals = header.value.split('.')
        return vals.reduce((acc, val) => acc[val], item)
      } else {
        return item[header.value]
      }
    }
  },
  computed: {
  },
  mounted () {
    // this.$nextTick(() => {
    // })
  }
}
</script>
