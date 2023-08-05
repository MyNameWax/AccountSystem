<template>
    <el-row type="flex" class="row-bg" justify="center">
        <el-col :xl="6" :lg="7">
            <h2>欢迎来到账号管理平台系统</h2>
            <el-image :src="require('@/assets/wax.jpg')" style="height: 180px; width: 180px;"></el-image>

            <p>感谢您的支持</p>
            <p>请完成用户注册</p>

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

                <el-form-item>
                    <el-button @click="register(formRef)" type="primary">注册</el-button>
                    <el-button @click="goLogin" >去登录</el-button>
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
/**
 * 简单类型看ref
 * 复杂类型看reactive
 */
import {onMounted, reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import router from "@/router";
import {get, post} from "@/request/service";


const userInfo = reactive({
    username: "",
    userPassword: "",
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

const register = async (formRef) => {
    if (formRef == null) return;
    formRef.validate(async (valid) => {
        if (valid) {
            let res = await post('/account/register', userInfo)
            if (res.code === 40101) {
                ElMessage.error(res.message)
            }
            ElMessage.success("注册成功");
            await router.push('/')
        }else {
            return false;
        }
    })


}
const goLogin = () => {
    router.push('/')
}


</script>
