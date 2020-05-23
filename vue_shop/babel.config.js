//项目发布阶段需要用到的babel插件
const prodPlugins = []
if(process.env.NODE_ENV === 'production'){
  prodPlugins.push('transform-remove-console')
}

module.exports = {
  "presets": [
    "@vue/cli-plugin-babel/preset"
  ],
  "plugins": [
    [
      "component",
      {
        "libraryName": "element-ui",
        "styleLibraryName": "theme-chalk"
      }
    ],
    //移除所有console,上面做了判断，只在发布阶段移除所有的console
    ...prodPlugins,
    //实现路由懒加载需要的插件
    "@babel/plugin-syntax-dynamic-import"
  ]
}