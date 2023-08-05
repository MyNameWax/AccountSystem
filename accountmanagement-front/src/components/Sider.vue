<script setup>
import { ref } from 'vue'
import router from "@/router";

const isCollapse = ref(true)
const handleOpen = (key, keyPath) => {
    // console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
    // console.log(key, keyPath)
}
</script>

<template>
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
        <el-radio-button :label="false">展开</el-radio-button>
        <el-radio-button :label="true">关闭</el-radio-button>
    </el-radio-group>
    <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            :collapse="isCollapse"
            @open="handleOpen"
            @close="handleClose"
    >
        <el-menu-item index="1" @click="router.push('/index')">
            <el-icon><House /></el-icon>
            <template #title >首页</template>
        </el-menu-item>
            <el-sub-menu index="2">
                <template #title>
                    <el-icon><User /></el-icon>
                    <span>账号管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="2-1" @click="router.push('/myaccount')">我的账号</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>

    </el-menu>
</template>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 150px;
    min-height: 400px;
}
</style>
