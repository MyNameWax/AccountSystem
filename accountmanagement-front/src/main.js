import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
document.title="账号管理平台"

const app = createApp(App);


// 通过遍历的方式注册所有 svg组件，会牺牲一点点性能
for (let i in ElementPlusIconsVue) {
    app.component(i, ElementPlusIconsVue[i])
}

app.use(ElementPlus).use(router).use(store).use(router).mount('#app')
