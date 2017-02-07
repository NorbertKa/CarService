<template>
  <div id="app">
      <main class="mdl-layout__content">
        <router-view></router-view>
      </main>
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<style>
    #app {
        padding-top: 1em;
        padding-left: 4em;
        padding-right: 4em;
    }
</style>
