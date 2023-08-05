
<script setup>

import Sider from "@/components/Sider.vue";
import Top from "@/components/top.vue";
import {onMounted, reactive, ref} from "vue";
import {changeTaskStatus, deleteTask, getTask, insertTask} from "@/api/task";
import {ElMessage} from "element-plus";
const taskList = ref([])

onMounted(async () => {
    await getData();
})
const getData = async () => {
    const res = await getTask();
    taskList.value = res.data;
}
const dialogVisible = ref(false)
const showModal = () => {
    dialogVisible.value = true;
};
const task = reactive({
    taskName: '',
});
const insertTaskClick = async (task) => {
    const res = await insertTask(task);
    if (res.code === 0) {
        ElMessage.success("操作成功");
        dialogVisible.value = false
        await getData();
        task.taskName = ''
    } else {
        ElMessage.error(res.description)
        dialogVisible.value = false
        task.taskName = ''
    }
}
const deleteTaskClick = async (id) => {
    const res = await deleteTask(id);
    if (res.code === 0) {
        ElMessage.success("删除成功")
        await getData();
    }else {
        ElMessage.error(res.description)
    }
}
const changeTaskStatusClick = async (id) => {
    const res = await changeTaskStatus(id);
    if (res.code === 0) {
        ElMessage.success("修改成功");
        await getData()
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

                <el-main >
                    <el-card class="box-card">
                        <template #header>
                            <div class="card-header">
                                <span>每日任务</span>
                                <el-button class="button" text @click="showModal">点击添加</el-button>
                            </div>


                        </template>

                        <template #default>
                            <div v-for="task in taskList" :key="task.id" class="text item" style="position: relative">{{ task.taskName }}
                                <el-popconfirm
                                    @confirm="deleteTaskClick(task.id)"
                                    title="你确定要删除吗？"
                                    confirm-button-text="确定"
                                    cancel-button-text="取消"
                                >
                                    <template #reference>
                                        <el-button type="text">☒</el-button>
                                    </template>
                                </el-popconfirm>

                                <el-popconfirm
                                    @confirm="changeTaskStatusClick(task.id)"
                                    title="你确定要修改任务状态吗？"
                                    confirm-button-text="确定"
                                    cancel-button-text="取消"
                                >
                                    <template #reference>
                                        <el-button type="text">☑</el-button>
                                    </template>
                                </el-popconfirm>

                                <el-tag v-if="task.status === 0" type="danger" style="position: absolute;right: 0px">未完成</el-tag>
                                <el-tag v-if="task.status === 1" type="success" style="position: absolute;right: 0px">已完成</el-tag>
                            </div>



                        </template>

                    </el-card>
                    <el-dialog title="新建任务"  v-model="dialogVisible" style="width: 500px">
                        <el-form :model="task" ref="form"  label-width="100px" >
                            <el-form-item label="任务" >
                                <el-input type="text" v-model="task.taskName" prop="taskName"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="insertTaskClick(task)">提交</el-button>
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

