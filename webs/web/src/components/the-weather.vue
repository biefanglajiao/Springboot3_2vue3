<template>
  <div >

  </div>

</template>


<script lang="ts">

import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";


export default defineComponent({
components: {
},
setup() {

  const getweathernow=()=>{
    axios.get("/weather/now").then((res) => {
      const data = res.data;
      if (data.success) {
        console.log(data);
      } else {
        console.log(data);
      }
  })
    const weather = () => {
console.log("aa")
    };

    onMounted(() => {
      weather();
    });


  }

  const getweather3d=()=>{
    axios.get("/weather/yubao").then((res) => {
      const data = res.data;
      if (data.success) {
        console.log(data);
      } else {
        console.log(data);
      }
    })
  }
  return {
    getweathernow,
    getweather3d,

  }
}
});
</script>