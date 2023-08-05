<script setup>
import {onMounted, ref} from 'vue'
import {getInfo} from "@/api/user";

const activeIndex = ref('1')
let userInfo = {
}
onMounted(async () => {
   const res =  await getInfo()
    userInfo = res.data
})

</script>

<template>
    <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            :ellipsis="false"
    >
        <el-menu-item index="0">
            <img src="../../src/assets/logo.jpg" style="width: 40px;height: 40px;margin-top: 10px">
        </el-menu-item>
        <div class="flex-grow" />
        <el-sub-menu index="2">
            <template #title><div>我的</div></template>
            <el-menu-item index="2-1">
                用户名: {{userInfo.username}}</el-menu-item>
            <el-menu-item index="2-2">用户角色 : {{userInfo.isAdmin ? '管理员' : '用户'}}</el-menu-item>

        </el-sub-menu>
    </el-menu>
</template>

<style scoped>
.flex-grow {
    flex-grow: 1;
}
</style>
