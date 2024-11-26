<style scoped>
.login-body {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f5f7fa;
}

.login-container {
    width: 400px;
}

.login-card {
    padding: 20px;
    border-radius: 10px;
}

.card-header {
    text-align: center;
    font-size: 20px;
    font-weight: bold;
    color: #333;
}

.login-form {
    margin-top: 20px;
}

.register-link {
    color: #409eff;
    cursor: pointer;
    text-decoration: underline;
}

.register-link:hover {
    color: #66b1ff;
}
</style>

<template>
    <div class="login-body">
        <div class="login-container">
            <el-card class="login-card" shadow="hover">
                <template #header>
                    <div class="card-header">管理员登录</div>
                </template>
                <div class="login-form">
                    <!-- 确保 el-form 的 model 正确绑定 -->
                    <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-width="100px">
                        <el-form-item label="账号：" prop="username">
                            <el-input type="text" v-model="loginForm.username" placeholder="请输入账号" clearable />
                        </el-form-item>
                        <el-form-item label="密码：" prop="password">
                            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" show-password />
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" :loading="loading" @click="handleLogin">
                                登录
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
import { Message } from "element-ui";

export default {
    name: "LoginView",
    data() {
        return {
            // 表单数据
            loginForm: {
                username: "",
                password: "",
            },
            // 表单验证规则
            loginRules: {
                username: [
                    { required: true, message: "请输入账号", trigger: "blur" },
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                ],
            },
            loading: false, // 登录加载状态
            // 模拟用户数据
            fakeUserData: {
                username: "admin",
                password: "123456",
            },
        };
    },
    methods: {
        handleLogin() {
            this.$refs.loginFormRef.validate((valid) => {
                if (!valid) {
                    return Message.error("请输入账号和密码！");
                }

                this.loading = true;

                setTimeout(() => {
                    if (
                        this.loginForm.username === this.fakeUserData.username &&
                        this.loginForm.password === this.fakeUserData.password
                    ) {
                        Message.success("登录成功！");
                        this.$router.push({ path: "/" });
                    } else {
                        Message.error("用户名或密码错误，请重试！");
                    }
                    this.loading = false;
                }, 1000);
            });
        },
    },
};
</script>
