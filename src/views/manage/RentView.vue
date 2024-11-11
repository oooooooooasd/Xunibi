<template>
    <div class="container">
        <div class="header">
            <el-input v-model="searchQuery" placeholder="请输入物品名称" style="width: 250px"></el-input>
            <el-button type="info" plain @click="search">查询</el-button>
            <el-button type="warning" plain @click="reset">重置</el-button>
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
        </div>

        <div class="table-wrapper">
            <el-table ref="filterTable" :data="tableData" style="width: 100%" border>
                <el-table-column prop="type" label="物品名称" align="center">
                </el-table-column>
                <el-table-column prop="coin_comspution" label="虚拟币价格" align="center">
                </el-table-column>
                <el-table-column prop="tag" label="标签" align="center"
                    :filters="[{ text: '工位', value: '工位' }, { text: '相机', value: '相机' }, { text: '其余设备', value: '其余设备' }]"
                    :filter-method="filterTag" filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.tag === '工位' ? 'primary' : 'success'">{{ scope.row.tag }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template v-slot="scope">
                        <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" plain @click="del(scope.row.teamId)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            searchQuery: null,
            tableData: [
                { type: '相机', coin_comspution: 50, tag: '相机' },
                { type: '工位', coin_comspution: 20, tag: '工位' },
                { type: '其余设备', coin_comspution: 30, tag: '其余设备' },
                { type: '相机', coin_comspution: 70, tag: '相机' },
                { type: '工位', coin_comspution: 40, tag: '工位' },
                { type: '其余设备', coin_comspution: 25, tag: '其余设备' }
            ]
        }
    },
    methods: {
        reset() {
            this.searchQuery = '';
            this.clearFilter();
        },
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        filterTag(value, row) {
            return row.tag === value;
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        }
    }
}
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
