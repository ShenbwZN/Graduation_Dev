function showChart(url, wayValue) {
    $.get(url, {way: wayValue}, function (data) {
        const x = data["xValue"];
        const y = data["yValue"];
        if (x === undefined) {
            let div_msg = document.getElementById("bar_div");
            div_msg.innerHTML = "数据加载错误";
            div_msg.style.color = "red";
            // alert("数据请求失败！");
        } else {
            let staTitle = document.getElementById("staTitle");
            if (staTitle != null) {
                staTitle.innerHTML = wayValue + "数据信息如下：";
                staTitle.style.fontSize = "22px";
            }
            console.log(staTitle);
            showBL(x, y, wayValue, "bar_div", "bar");
            showBL(x, y, wayValue, "line_div", "line");
        }
    });
}


/**
 * 展示：line, bar 图
 * @param x x轴
 * @param y y轴
 * @param sta 标题
 * @param myDiv 展示的div
 * @param e_type 图的类型
 */
function showBL(x, y, sta, myDiv, e_type) {
    console.log(x);
    console.log(y);

    // 基于准备好的dom，初始化echarts实例
    const myChart = echarts.init(document.getElementById(myDiv));
    // 指定图表的配置项和数据
    const option = {
        title: {
            text: sta
        },
        tooltip: {},
        legend: {
            data: ['水位均值']
        },
        xAxis: {
            // type: "time",
            // name: "时间/月",
            data: x
        },
        yAxis: {
            type: "value",
            name: "水位均值"
        },
        series: [{
            name: '水位均值',
            type: e_type,
            data: y
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}