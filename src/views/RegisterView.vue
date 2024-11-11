<template>
    <div class="register-body">
        <div class="register-content">
            <el-card v-if="!isRegister" class="register-card" :body-style="{ padding: '0 20px' }">
                <template #header>
                    <h2 class="header-title">用户注册</h2>
                </template>
                <div class="register-form">
                    <div class="back" @click="goBack">
                        <p>返回</p>
                        <svg t="1723536226845" class="icon" viewBox="0 0 1237 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="1454" width="40" height="40">
                            <path
                                d="M481.216 273.002667V0L0 477.802667l481.216 477.994666V675.84c343.68 0 584.32 109.205333 756.117333 348.181333-68.693333-341.376-274.901333-682.602667-756.117333-750.997333"
                                fill="#4AD9FD" p-id="1455"></path>
                        </svg>
                    </div>

                    <el-form v-model="registerForm" label-width="100px">
                        <el-form-item label="姓名：" prop="uname">
                            <el-input type="text" v-model="registerForm.uname" placeholder="请输入姓名" />
                        </el-form-item>

                        <el-form-item label="性别：" prop="sex">
                            <el-select v-model="registerForm.sex" placeholder="请选择性别" style="margin-left: 10px;">
                                <el-option label="男" value="1"></el-option>
                                <el-option label="女" value="0"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="邮箱：" prop="email">
                            <el-input type="text" v-model="registerForm.email" placeholder="请输入邮箱" />
                        </el-form-item>
                        <el-form-item label="常用手机：" prop="telephone">
                            <el-input type="text" v-model="registerForm.telephone" placeholder="请输入手机号" />
                        </el-form-item>
                        <el-form-item label="身份证号：" prop="idcard">
                            <el-input type="text" v-model="registerForm.idcard" placeholder="请输入身份证号" />
                        </el-form-item>
                        <div class="form-buttons">
                            <el-button color="#4AD9FD" type="primary" @click="toRegister">注册</el-button>
                            <el-button color="#5941a6" type="warning" @click="toReset">重置</el-button>
                        </div>
                    </el-form>
                </div>
            </el-card>

            <!-- Success Message -->
            <el-card v-else class="register-card" :body-style="{ padding: '0 20px' }">
                <el-result icon="success" title="注册成功" :sub-title="`${count}秒后自动返回登陆页面`">
                    <template #extra>
                        <el-button type="primary" @click="goBack">返回</el-button>
                    </template>
                </el-result>
            </el-card>
        </div>
    </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-ui';

export default {
    data() {
        return {
            count: 5,
            isRegister: false,
            registerForm: {
                uname: '',
                sex: 1,
                email: '',
                telephone: '',
                idcard: ''
            }
        };
    },
    created() {
        this.count = 5;
    },
    methods: {
        toRegister() {
            ElMessageBox.alert(
                '请牢记您的账号和密码！<br> 您的账号是：2041036529<br>您的密码是：792364@qq.com',
                '提示',
                {
                    dangerouslyUseHTMLString: true,
                    draggable: true,
                    overflow: true,
                    showClose: false,
                    confirmButtonText: '确定',
                    type: 'warning'
                }
            ).then(() => {
                this.startCountdown();
                this.isRegister = true;
            });
        },
        goBack() {
            if (this.count !== -1) {
                this.count = -1;
            }
            this.$router.push({ path: '/login' });
            this.isRegister = false;
        },
        startCountdown() {
            const interval = setInterval(() => {
                if (this.count > 0) {
                    this.count--;
                } else {
                    clearInterval(interval);
                    this.goBack();
                }
            }, 1000);
        },
        toReset() {
            this.registerForm = {
                uname: '',
                sex: 1,
                email: '',
                telephone: '',
                idcard: ''
            };
        }
    }
};
</script>

<style lang="less" scoped>
/* General Styles */
.register-body {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f4f7fc;
}

.register-content {
    width: 100%;
    max-width: 600px;
    padding: 30px;
}

.register-card {
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}

.header-title {
    font-size: 24px;
    font-weight: bold;
    color: #4a90e2;
    text-align: center;
    padding: 20px 0;
}

.register-form {
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

.el-radio-group {
    display: flex;
    justify-content: space-between;
}

.el-button {
    font-size: 14px;
    padding: 10px;
    border-radius: 6px;
    width: 48%;
}

.form-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.back {
    cursor: pointer;
    display: flex;
    align-items: center;
    color: #4a90e2;
    font-size: 16px;
    margin-bottom: 10px;
}

.back svg {
    margin-left: 8px;
}

.el-result .el-button {
    width: 100%;
}

.el-result {
    text-align: center;
}

.el-result .el-button {
    font-size: 16px;
    padding: 10px;
    border-radius: 6px;
}
</style>
