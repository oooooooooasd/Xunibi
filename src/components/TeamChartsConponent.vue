<template>
    <div>
        <div class="tip-message" style="font-size: 12px; color: #888; margin-bottom: 10px; text-align: center;">
            请在下方输入各个成果的权重,默认三个重要性相同
        </div>
        <div class="input-container">

            <div class="input-group">
                <label for="paper-weight">论文:</label>
                <el-input-number id="paper-weight" v-model="weights.w1" :min="0" :max="10" size="mini">
                </el-input-number>
            </div>
            <div class="input-group">
                <label for="competition-weight">竞赛:</label>
                <el-input-number id="competition-weight" v-model="weights.w2" :min="0" :max="10" size="mini">
                </el-input-number>
            </div>
            <div class="input-group">
                <label for="training-weight">培训:</label>
                <el-input-number id="training-weight" v-model="weights.w3" :min="0" :max="10" size="mini">
                </el-input-number>
            </div>
        </div>
        <div ref="TeamChart" class="chart-container"></div>
    </div>
</template>

<script>
import * as echarts from "echarts";

export default {
    props: {
        // 接收父组件传递的团队数据
        teamData: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            weights: {
                w1: 1, // 论文初始权重
                w2: 1, // 竞赛初始权重
                w3: 1  // 培训初始权重
            },
            TeamChart: null
        };
    },
    watch: {
        teamData(newData) {
            this.updateChart(newData);
        },
        // 监听权重变化，实时更新图表
        weights: {
            handler: "updateChart",
            deep: true
        }
    },
    created() {
    },
    mounted() {
        this.initChart();
    },
    methods: {
        initChart() {
            const chartDom = this.$refs.TeamChart;
            this.TeamChart = echarts.init(chartDom);
            this.updateChart();
        },
        updateChart() {
            if (!this.teamData.length) return;  // 确保 data 不为空
            const computedData = this.teamData.map((team) => {
                const contribution =
                    team.paper * this.weights.w1 +
                    team.competition * this.weights.w2 +
                    team.training * this.weights.w3;
                return { name: team.name, contribution };
            });

            const option = {
                title: {
                    text: "团队贡献度柱状图"
                },
                tooltip: {
                    trigger: "axis",
                    axisPointer: { type: "shadow" }
                },
                xAxis: {
                    type: "category",
                    data: computedData.map((item) => item.name)
                },
                yAxis: {
                    type: "value"
                },
                series: [
                    {
                        name: "贡献度",
                        type: "bar",
                        data: computedData.map((item) => item.contribution),
                        itemStyle: { color: "#409EFF" }
                    }
                ]
            };

            this.TeamChart.setOption(option);
        }
    }
};
</script>

<style scoped>
.chart-container {
    width: 500px;
    height: 400px;
    margin-top: 20px;
}

.input-container {
    margin-bottom: 20px;
    display: flex;
    width: 400px;
    gap: 20px;
    align-items: center;
}
</style>