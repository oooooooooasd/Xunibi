<template>
    <div>
        <div class="header">
            <el-row :gutter="20">
                <el-col :span="6">
                    <div class="statistic-box">
                        <div class="title">入住团队总数</div>
                        <div class="value">{{ value2 }}</div>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="statistic-box">
                        <div class="title">入住总人数</div>
                        <div class="value">{{ value1 }}</div>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="statistic-box">
                        <div class="title">今日签到人数</div>
                        <div class="value">
                            <i class="el-icon-s-flag" style="color: red"></i>
                            {{ value1 }}
                            <i class="el-icon-s-flag" style="color: blue"></i>
                        </div>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="statistic-box">
                        <div class="title">今日值日</div>
                        <div class="value">
                            <i class="el-icon-s-flag" style="color: red"></i>
                            {{ value3 }} 团队
                            <i class="el-icon-s-flag" style="color: blue"></i>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>

        <div class="main">



            <el-tabs type="border-card">
                <el-tab-pane label="首页" lazy>

                </el-tab-pane>
                <el-tab-pane label="收入数据" lazy>
                    <IncomeComponent :chartData="charData"></IncomeComponent>
                </el-tab-pane>
                <el-tab-pane label="支出数据" lazy>
                    <ExpandComponent :chartData="charExpData"></ExpandComponent>
                </el-tab-pane>
                <el-tab-pane label="团队数据">
                    <el-row :gutter="15">
                        <el-col :span="12">
                            <div class="chart-container">
                                <div class="chart-title">团队贡献度可视化</div>
                                <TeamChartsConponent :teamData="teamData" />
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="chart-container">

                                <TeamNumsComponent :teamNumsData="teamNumsData">
                                </TeamNumsComponent>
                            </div>
                        </el-col>
                    </el-row>
                </el-tab-pane>
            </el-tabs>

        </div>
    </div>
</template>

<script>
import axios from "axios";
import IncomeComponent from "@/components/IncomeComponent.vue";
import TeamChartsConponent from "@/components/TeamChartsConponent.vue";
import TeamNumsComponent from "@/components/TeamNumsComponent.vue";
import ExpandComponent from "@/components/ExpandComponent.vue";
import * as echarts from "echarts";


export default {
    components: {
        IncomeComponent,
        TeamChartsConponent,
        TeamNumsComponent,
        ExpandComponent
    },
    data() {

        return {
            like: true,
            value1: 4154.564,
            value2: 1314,
            value3: "test",
            title: "增长人数",
            charData: [
            ],
            charExpData: [
            ],

            teamData: [

            ],
            teamNumsData: [
            ]
        };
    },




    created() {

    },
    mounted() {
        this.getTeamAchievementSummaries();
        this.getTeamNums();
        this.getTeamWithMembers();
        this.getMemberNums();
        this.getMonthlySpend();
        this.getMonthlyEarn();
    },
    methods: {

        handleTabClick() {

            this.$nextTick(() => {
                this.$refs.IncomeChart && this.$refs.IncomeChart.resize();
                this.$refs.ExpandChart && this.$refs.ExpandChart.resize();
            });
        },
        getTeamNums() {
            axios.get("http://localhost:8080/chart/teamnums")
                .then(response => {
                    if (response.data.code === 200) {
                        this.value2 = response.data.data;  // 更新value2为团队总数
                        // console.log("value2:", response.data)
                    }
                })
                .catch(error => {
                    console.error("获取团队总数失败", error);
                });
        },
        getMemberNums() {
            axios.get("http://localhost:8080/chart/usernums")
                .then(response => {
                    if (response.data.code === 200) {
                        // console.log("value:", response.data)
                        this.value1 = response.data.data;  // 更新value1为用户总数
                    }
                })
                .catch(error => {
                    console.error("获取用户总数失败", error);
                });

        },
        getTeamWithMembers() {
            axios.get("http://localhost:8080/chart/teamwithmeb").then(response => {
                if (response.data.code === 200) {
                    // console.log(response.data);
                    this.teamNumsData = response.data.data.map(item => ({
                        name: item.teamName,
                        personNum: item.teamMemberCount
                    }));
                }
            }).catch(error => {
                console.error("获取团队用户数失败", error)
            })
        },
        getTeamAchievementSummaries() {
            axios.get("http://localhost:8080/chart/teamwithachive").then(response => {
                if (response.data.code === 200) {
                    // console.log("teamdata:", response.data);

                    this.teamData = response.data.data.map(item => ({
                        name: item.teamName,
                        paper: item.paperCount,
                        competition: item.competitionCount,
                        training: item.otherCount,
                    }))
                    // console.log("this.data:", this.teamData);
                }
            }).catch(error => {
                console.error("获取团队用户数失败", error)
            })
        },
        getMonthlySpend() {
            axios
                .get("http://localhost:8080/chart/monwithspend")
                .then((response) => {
                    if (response.data.code === 200) {
                        const rawData = response.data.data;
                        console.log("支出转化前:", rawData);

                        // 获取当前年份和月份
                        const currentYear = new Date().getFullYear();
                        const months = Array.from({ length: 12 }, (_, i) => `${currentYear}-${(i + 1).toString().padStart(2, "0")}`);

                        // 提取所有类别
                        const categories = [...new Set(rawData.map(item => item.description))];

                        // 创建数据集
                        const dataset = [
                            ['product', ...months], // 表头：产品类别和月份
                            ...categories.map(category => {
                                return [
                                    category,
                                    ...months.map(month => {
                                        const item = rawData.find(d => d.description === category && d.month === month);
                                        return item ? item.totalExpenditure : 0;
                                    })
                                ];
                            })
                        ];

                        console.log("支出转化后:", dataset);

                        // 设置图表的最终数据格式
                        this.charExpData = dataset;

                    }
                })
                .catch((error) => {
                    console.error("获取每月支出数据失败", error);
                });
        },
        getMonthlyEarn() {
            axios
                .get("http://localhost:8080/chart/monwithearn")
                .then((response) => {
                    if (response.data.code === 200) {
                        const rawData = response.data.data;
                        console.log("收入转化前:", rawData);

                        // 获取当前年份和月份
                        const currentYear = new Date().getFullYear();
                        const months = Array.from({ length: 12 }, (_, i) => `${currentYear}-${(i + 1).toString().padStart(2, "0")}`);

                        // 提取所有类别
                        const categories = [...new Set(rawData.map(item => item.description))];

                        // 创建数据集
                        const dataset = [
                            ['product', ...months], // 表头：产品类别和月份
                            ...categories.map(category => {
                                return [
                                    category,
                                    ...months.map(month => {
                                        const item = rawData.find(d => d.description === category && d.month === month);
                                        return item ? item.totalExpenditure : 0;
                                    })
                                ];
                            })
                        ];

                        console.log("转化后:", dataset);

                        // 设置图表的最终数据格式
                        this.charData = dataset;

                    }
                })
                .catch((error) => {
                    console.error("获取每月收入数据失败", error);
                });
        }

    }

};
</script>
<style scoped>
/* 整体容器 */
header,
.main,
.footer {
    margin: 20px;
}

/* 头部统计数据区域 */
.statistic-box {
    background: #fff;
    border: 1px solid #ddd;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

/* 鼠标悬停时增加阴影 */
.statistic-box:hover {
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
}

/* 标题样式 */
.statistic-box .title {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    margin-bottom: 10px;
}

/* 数值样式 */
.statistic-box .value {
    font-size: 24px;
    font-weight: 600;
    color: #4CAF50;
    margin-top: 5px;
}

/* 互动图标的样式 */
.like {
    cursor: pointer;
    font-size: 25px;
    display: inline-block;
    margin-top: 10px;
    transition: transform 0.2s;
}

/* 鼠标悬停时加上放大效果 */
.like:hover {
    transform: scale(1.1);
}

/* 中间图表区 */
.main {
    margin-top: 20px;
}

/* 使图表的每一块区域有间距 */
.el-row {
    margin-bottom: 20px;
}

/* 在图表块上加上阴影效果 */
.el-col {
    padding: 10px;
}

footer {
    text-align: center;
    color: #777;
    font-size: 14px;
    margin-top: 30px;
}

/* 调整响应式设计，屏幕较小的情况 */
@media (max-width: 768px) {
    .statistic-box {
        margin-bottom: 20px;
    }

    .el-col {
        padding: 5px;
    }

    .main {
        margin-top: 10px;
    }
}



/* 图表容器样式 */
.chart-container {
    background: #fff;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    text-align: center;
    position: relative;
}

/* 图表容器的鼠标悬停效果 */
.chart-container:hover {
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

/* 图表标题样式 */
.chart-title {
    font-size: 18px;
    font-weight: 600;
    color: #333;
    margin-bottom: 15px;
}
</style>
