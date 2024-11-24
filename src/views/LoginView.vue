<template>
    <div class="login-body">
        <div class="login-container">
            <el-card class="login-card">
                <template #header>
                    <div class="card-header">管理员登录</div>
                </template>
                <div class="login-form">
                    <el-form v-model="loginForm" label-width="100px">
                        <el-form-item label="账号：" prop="username">
                            <el-input type="text" v-model="loginForm.username" placeholder="请输入账号" />
                        </el-form-item>
                        <el-form-item label="密码：" prop="password">
                            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" />
                        </el-form-item>

                        <el-button class="login-btn" type="primary" @click="Login">登录</el-button>
                        <div class="register-link">
                            <a @click="toRegister">还没有账号? 点击注册</a>
                        </div>
                    </el-form>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'LoginView',
    data() {
        return {
            loginForm: {
                username: '',
                password: '',
                code: ''
            }
        };
    },
    methods: {
        toRegister() {
            this.$router.push({ path: '/toRegister' });
        },
        async Login() {
            try {
                const response = await axios.post('http://localhost:8080/login', {
                    username: this.loginForm.username,
                    password: this.loginForm.password
                });
                console.log('Login successful:', response.data);
                // Redirect to home page after successful login
                this.$router.push({ path: '/' });
            } catch (error) {
                console.error('Login Failed:', error.response ? error.response.data : error.message);
            }
        }
    }
};
</script>

<style lang="less" scoped>
/* General Styling */
.login-body {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f4f7fc;
}

.login-container {
    width: 100%;
    max-width: 400px;
    padding: 30px;
}

.logo {
    text-align: center;
    font-size: 32px;
    font-weight: bold;
    color: #4a90e2;
    margin-bottom: 20px;
}

.login-card {
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.card-header {
    font-size: 18px;
    text-align: center;
    font-weight: bold;
    color: #333;
    padding: 10px 0;
}

.login-form {
    padding: 20px;
}

.el-form-item {
    margin-bottom: 20px;
}

.el-input {
    font-size: 14px;
    padding: 10px;
    border-radius: 6px;
}

.el-button {
    font-size: 14px;
    padding: 10px;
    border-radius: 6px;
    width: 100%;
    height: 40px;
}

.login-btn {
    margin-top: 10px;
}

.register-link {
    text-align: center;
    margin-top: 20px;
}

.register-link a {
    color: #4a90e2;
    font-size: 14px;
    text-decoration: none;
}

.register-link a:hover {
    text-decoration: underline;
}
</style>
