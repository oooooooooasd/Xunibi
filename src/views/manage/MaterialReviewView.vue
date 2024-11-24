<template>
    <div class="container">
        <div class="header">
            <el-input v-model="searchQuery" placeholder="请输入物品名称" style="width: 250px"></el-input>
            <el-button type="info" plain @click="search">查询</el-button>
            <el-button type="warning" plain @click="reset">重置</el-button>
        </div>

        <div class="table-wrapper">
            <el-table ref="filterTable" :data="tableData" style="width: 100%" border>
                <el-table-column prop="teamId" label="团队Id" align="center">
                </el-table-column>
                <el-table-column prop="teamName" label="团队名称" align="center">
                </el-table-column>
                <el-table-column prop="material" label="审核材料" align="center">
                </el-table-column>
                <el-table-column prop="reviewProcess" label="审核进度" align="center">
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template v-slot="scope">
                        <el-button size="mini" type="primary" plain :disabled="scope.row.reviewProcess === '已通过'"
                            @click="openDialog(scope.row)">审核通过</el-button>
                        <el-button size="mini" type="danger" plain
                            @click="handleNoPass(scope.row.teamId)">审核不通过</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 审核通过弹窗 -->
        <el-dialog title="增加虚拟币" :visible.sync="dialogVisible" width="400px">
            <div>
                <el-form>
                    <el-form-item label="增加虚拟币数量">
                        <el-input-number v-model="coinAmount" :min="0" :max="1000" label="虚拟币" />
                    </el-form-item>
                </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handlePass">确认</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            searchQuery: null,
            tableData: [], // 假数据容器
            dialogVisible: false, // 控制弹窗显示
            selectedRow: null, // 当前操作的行数据
            coinAmount: 0, // 增加的虚拟币数量
        };
    },
    created() {
        // 生成假数据
        this.tableData = Array.from({ length: 10 }).map((_, index) => ({
            teamId: `T${1000 + index}`, // 假团队ID
            teamName: `团队${index + 1}`, // 假团队名称
            material: `材料${index + 1}`, // 假审核材料
            reviewProcess: index % 3 === 0 ? "未审核" : "审核中", // 审核进度模拟
        }));
    },
    methods: {
        openDialog(row) {
            this.selectedRow = row;
            this.coinAmount = 0;
            this.dialogVisible = true;
        },
        handlePass() {
            if (!this.selectedRow) return;
            axios
                .post("review/audit/apporve", {
                    teamId: this.selectedRow.teamId,
                    coinAmount: this.coinAmount,
                })
                .then((response) => {
                    if (response.data.success) {
                        this.$message.success("审核通过成功");
                        this.dialogVisible = false;
                        this.selectedRow.reviewProcess = "已通过"; // 更新状态
                    } else {
                        this.$message.error(response.data.message || "审核通过失败");
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error("审核通过失败");
                });
        },
        handleNoPass(teamId) {
            axios
                .post("review/audit/reject", {
                    teamId: teamId,
                })
                .then((response) => {
                    if (response.data.success) {
                        this.$message.success("审核不通过成功");
                    } else {
                        this.$message.error(response.data.message || "审核不通过失败");
                    }
                })
                .catch((error) => {
                    console.error(error);
                    this.$message.error("审核不通过失败");
                });
        },
        reset() {
            this.searchQuery = "";
        },
    },
};
</script>

<style scoped>
.container {
    padding: 20px;
    background-color: #f9f9f9;
}

.header {
    display: flex;
    gap: 10px;
    margin-bottom: 15px;
}

.header .el-input {
    max-width: 300px;
}

.table-wrapper {
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    background-color: #ffffff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 10px;
}
</style>
