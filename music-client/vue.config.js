const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
let path= require('path')
const {config} =require('process')
module.exports  ={
  devServer:{
    port:8081
  }
}
