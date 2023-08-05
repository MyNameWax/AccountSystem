import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegView from "@/views/RegView.vue";
import IndexView from "@/views/IndexView.vue";
import MyAccount from "@/views/MyAccount.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      title: "账号管理平台"
    }
  },
  {
    path: '/reg',
    name: 'reg',
    component: RegView,
    meta: {
      title: "账号管理平台"
    }
  },
  {
    path: '/index',
    name: 'index',
    component: IndexView
  },
  {
    path: '/myaccount',
    name: 'myaccount',
    component: MyAccount
  }
]



const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
