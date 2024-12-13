const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
let path= require('path')
const {config} =require('process')
module.exports  ={
  devServer:{
    port:8081,
    proxy: {
      '/163id': { // 这里的`/api`是前端请求的路径前缀
        target: 'http://music.163.com/api/cloudsearch/pc', // 你的目标服务器地址
        changeOrigin: true, // 是否改变源
        pathRewrite: {
          '^/163id': '' // 重写路径：去掉路径中的`/api`
        }
      },
      '/lrc': { // 这里的`/api`是前端请求的路径前缀
        target: 'https://music.163.com/api/song/lyric', // 你的目标服务器地址
        changeOrigin: true, // 是否改变源
        pathRewrite: {
          '^/lrc': '' // 重写路径：去掉路径中的`/api`
        }
      }
    }
  },
  
}
