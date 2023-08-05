
<script setup>

import Sider from "@/components/Sider.vue";
import Top from "@/components/top.vue";
import {onMounted, reactive, ref} from "vue";
import {DeleteAccountById, getById, getList, insertInfo, updateById} from "@/api/account";
import {ElMessage} from "element-plus";

const tableData = ref([
])
const searchText = ref('')

onMounted(async () => {
    await getData();
})

const getData = async () => {
    if (searchText.value === '') {
        const res = await getList();
        tableData.value = res.data.records
    }
    const res = await getList(searchText.value)
    tableData.value = res.data.records
}
// 删除
const deleteByIdClick = async (id) => {
    const res = await DeleteAccountById(id);
    console.log(res)
    if (res.code === 0) {
        ElMessage.success("删除成功")
        await getData();
    } else {
        ElMessage.error(res.description)
    }
}
let AccountInfo = ref({

})

const dialogVisible = ref(false)
const insertdialog = ref(false)
const showModal = async (id) => {
    dialogVisible.value = true;
    //TODO 先查询出来进行数据回显
    const res = await getById(id);
    AccountInfo.value = res.data
    console.log(AccountInfo._rawValue)
};
//修改
const updateByIdClick = async (AccountInfo) => {
    const res = await updateById( JSON.parse(JSON.stringify(AccountInfo)));
    if (res.code === 0) {
        dialogVisible.value = false;
        ElMessage.success("修改成功")
        AccountInfo.value = null;
        await getData();
    } else {
        dialogVisible.value = false;
        ElMessage.error(res.description)
        AccountInfo.value = null;
    }
}
// 新增
const insertData = async () => {
    insertdialog.value = true
}
const insertAccountClick = async (AccountInfo) => {
    const res = await insertInfo(AccountInfo);
    if (res.code === 0) {
        ElMessage.success("新增成功")
        insertdialog.value = false
        AccountInfo.value = null;
        await getData();
    } else {
        ElMessage.error(res.description)
    }
}


</script>
<template>

    <div class="common-layout">

        <el-container>
            <el-header>
                <top></top>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <sider></sider>
                </el-aside>
                <el-main>
                    <el-input v-model="searchText" placeholder="请输入平台名称" clearable style="width: 200px;"/>
                    <el-button type="primary" style="margin-left: 10px;" @click="getData">搜索</el-button>
                    <el-button type="primary" style="margin-left: 10px;" @click="insertData(AccountInfo)">新增</el-button>
                <el-table :data="tableData" stripe style="width: 100%">

                    <el-table-column prop="name" label="平台" width="350" />
                    <el-table-column prop="account" label="账号" width="350" />
                    <el-table-column prop="password" label="密码" width="350"/>
                    <el-table-column label="操作" width="200px">
                        <template v-slot="scope">
                            <el-button type="primary" text @click="showModal(scope.row.id)">修改</el-button>
                            <el-button type="danger" text @click="deleteByIdClick(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                    <el-dialog title="请修改信息"  v-model="dialogVisible" style="width: 500px">
                        <el-form :model="AccountInfo" ref="form"  label-width="100px" >
                            <el-form-item label="名称" prop="name">
                                <el-input type="text" v-model="AccountInfo.name" ></el-input>
                            </el-form-item>
                            <el-form-item label="账号" prop="account">
                                <el-input type="text" v-model="AccountInfo.account" ></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input type="text" v-model="AccountInfo.password" ></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="updateByIdClick(AccountInfo)">修改</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <el-dialog title="请添加信息"  v-model="insertdialog" style="width: 500px">
                        <el-form :model="AccountInfo" ref="insertform"  label-width="100px" >
                            <el-form-item label="名称" prop="name">
                                <el-input type="text" v-model="AccountInfo.name" ></el-input>
                            </el-form-item>
                            <el-form-item label="账号" prop="account">
                                <el-input type="text" v-model="AccountInfo.account" ></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input type="text" v-model="AccountInfo.password" ></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="insertAccountClick(AccountInfo)">新增</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                </el-main>

            </el-container>

        </el-container>

    </div>

</template>
<style>
* {
    margin: 0;
    padding: 0;
}
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    width: 480px;
}

</style>

