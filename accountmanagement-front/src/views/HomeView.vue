<template>
    <el-row type="flex" class="row-bg" justify="center">
        <el-col :xl="6" :lg="7">
            <h2>欢迎来到账号管理平台系统</h2>
            <el-image :src="require('@/assets/wax.jpg')" style="height: 180px; width: 180px;"></el-image>

            <p>您敢存,我敢守</p>
            <p>我在努力变好,可是众味难调</p>

        </el-col>

        <el-col :span="1">
            <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :xl="6" :lg="7">
            <el-form :model="userInfo" :rules="rules" label-width="80px" ref="formRef">
                <el-form-item label="用户名" prop="username" style="width: 380px;">
                    <el-input v-model="userInfo.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="userPassword" style="width: 380px;">
                    <el-input v-model="userInfo.userPassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label="验证码" prop="code" style="width: 380px;">
                    <el-input v-model="userInfo.code" style="width: 172px; float: left" maxlength="5"></el-input>
                    <el-image class="captchaImg" :src="captchaImg" @click="getCaptchaImg"></el-image>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="userLogin(formRef)">登录</el-button>
                    <el-button @click="register">注册</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>
<style>
* {
    margin: 0;
    padding: 0;
}

.el-row {
    background-color: #fafafa;
    height: 100%;
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: center;
}

.el-divider {
    height: 200px;
}

.captchaImg {
    float: left;
    margin-left: 8px;
    border-radius: 4px;
}
</style>
<script setup>

import {login} from "@/api/user";
/**
 * 简单类型看ref
 * 复杂类型看reactive
 */
import {onMounted, reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import router from "@/router";
import {get} from "@/request/service";


const userInfo = reactive({
    username: "",
    userPassword: "",
    code: ""
})

const formRef = ref(null)
const captchaImg = ref('')

const rules = ref({
        username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        userPassword: [
            {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        code: [
            {required: true, message: '请输入验证码', trigger: 'blur'},
        ],
    }
)
onMounted(async () => {
    getCaptchaImg();
})
const getCaptchaImg = async () => {
    const res = await get('/account/getCaption')
    captchaImg.value = res.data
}
const userLogin = async (formRef) => {
    try {
        if (formRef == null) return;
        formRef.validate(async (valid) => {
            if (valid) {
                let res = await login(userInfo);
                if (res.data == null) {
                    ElMessage.error(res.description)
                    getCaptchaImg();
                    return
                }
                ElMessage.success("登录成功")
                router.push("/index")
            } else {
                return false;
            }
        })
    } catch (e) {
        console.error(e)

    }
}

const register = () => {
    router.push("/reg")
}


</script>
