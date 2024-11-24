<template>
    <div ref="IncomeChart" style="width: 100%;height: 600px;"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
    props: {
        chartData: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            IncomeChart: null,
        };
    },
    mounted() {
        // window.onload = () => {
        //     this.initChart(); // 在窗口完全加载后初始化图表
        // };
        this.$nextTick(() => {
            this.initChart(); // 等待 DOM 渲染完成后初始化图表
        });
        window.addEventListener("resize", this.resizeChart); // 窗口大小变化时调整图表尺寸

    },
    beforeDestroy() {
        window.removeEventListener("resize", this.resizeChart); // 防止内存泄漏
    },
    watch: {
        chartData: {
            immediate: true,
            handler(newValue) {
                if (this.IncomeChart) {
                    this.IncomeChart.setOption({
                        dataset: {
                            source: newValue,
                        },
                    });
                }
            },
        },
    },
    methods: {
        resizeChart() {
            if (this.IncomeChart) {
                this.IncomeChart.resize(); // 调整图表尺寸
            }
        },
        initChart() {
            const chartDom = this.$refs.IncomeChart;
            this.IncomeChart = echarts.init(chartDom);

            const option = {
                legend: {},
                tooltip: {
                    trigger: "axis",
                    showContent: false,
                },
                dataset: {
                    source: this.chartData,
                },
                xAxis: { type: "category" },
                yAxis: { gridIndex: 0 },
                grid: { top: "55%" },
                series: [
                    { type: "line", smooth: true, seriesLayoutBy: "row", emphasis: { focus: "series" } },
                    { type: "line", smooth: true, seriesLayoutBy: "row", emphasis: { focus: "series" } },
                    { type: "line", smooth: true, seriesLayoutBy: "row", emphasis: { focus: "series" } },
                    { type: "line", smooth: true, seriesLayoutBy: "row", emphasis: { focus: "series" } },
                    {
                        type: "pie",
                        id: "pie",
                        radius: "30%",
                        center: ["50%", "25%"],
                        emphasis: { focus: "self" },
                        label: { formatter: "{b}: {@currentMonth} ({d}%)" },
                        encode: { itemName: "product", value: "currentMonth", tooltip: "currentMonth" },
                    },
                ],
            };

            this.IncomeChart.setOption(option);

            this.IncomeChart.on("updateAxisPointer", (event) => {
                const xAxisInfo = event.axesInfo[0];
                if (xAxisInfo) {
                    const dimension = xAxisInfo.value + 1;
                    this.IncomeChart.setOption({
                        series: {
                            id: "pie",
                            label: { formatter: "{b}: {@[" + dimension + "]} ({d}%)" },
                            encode: { value: dimension, tooltip: dimension },
                        },
                    });
                }
            });
        },
    },
};
</script>

<style scoped></style>
