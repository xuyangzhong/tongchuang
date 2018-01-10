window.addEventListener("resize", resizeCanvas, false);

function requestMapDetail() {
    var mapDetail = $.ajax({
        url: '/map/maplsit.do',
        type: "POST",
        dataType: "json",
        success: function (res) {
            var province, user_num, user_lists;
            res.forEach(function (element) {
                province = element.provinceModel;
                user_num = element.user_num;
                user_lists = res.user_lists;
            });
        }
    });
}
function resizeCanvas() {
	var  chart=document.getElementById("chart");
    chart.style.width = window.innerWidth;
    console.log(window.innerWidth);
    chart.style.height = window.innerHeight;
}

   // 初始化图表标签
    var myChart = echarts.init(document.getElementById('chart'));
    var option = {
        backgroundColor: 'rgb(247,247,247)',
    title : {
        text: '校友分布图',
        x:'center'
    },
    tooltip : {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        x:'left',
        data:['校友数量']
    },
    dataRange: {
        min: 0,
        max: 2500,
        x: 'left',
        y: 'bottom',
        text:['高','低'],           // 文本，默认为数值文本
        calculable : true,
        show:false
    },
    toolbox: {
        show: false,
        orient : 'vertical',
        x: 'right',
        y: 'center',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    roamController: {
        show: false,
        x: 'right',
        mapTypeControl: {
            'china': true
        }
    },
    series : [
        {
            name: 'classmates',
            type: 'map',
            mapType: 'china',
            roam: false,
            selectedMode:'single',
            itemStyle:{
                normal:{label:{show:true}},
                emphasis:{label:{show:true}}
            },
            data:[
                {name: '北京',value: Math.round(Math.random()*1000)},
                {name: '天津',value: Math.round(Math.random()*1000)},
                {name: '上海',value: Math.round(Math.random()*1000)},
                {name: '重庆',value: Math.round(Math.random()*1000)},
                {name: '河北',value: Math.round(Math.random()*1000)},
                {name: '河南',value: Math.round(Math.random()*1000)},
                {name: '云南',value: Math.round(Math.random()*1000)},
                {name: '辽宁',value: Math.round(Math.random()*1000)},
                {name: '黑龙江',value: Math.round(Math.random()*1000)},
                {name: '湖南',value: Math.round(Math.random()*1000)},
                {name: '安徽',value: Math.round(Math.random()*1000)},
                {name: '山东',value: Math.round(Math.random()*1000)},
                {name: '新疆',value: Math.round(Math.random()*1000)},
                {name: '江苏',value: Math.round(Math.random()*1000)},
                {name: '浙江',value: Math.round(Math.random()*1000)},
                {name: '江西',value: Math.round(Math.random()*1000)},
                {name: '湖北',value: Math.round(Math.random()*1000)},
                {name: '广西',value: Math.round(Math.random()*1000)},
                {name: '甘肃',value: Math.round(Math.random()*1000)},
                {name: '山西',value: Math.round(Math.random()*1000)},
                {name: '内蒙古',value: Math.round(Math.random()*1000)},
                {name: '陕西',value: Math.round(Math.random()*1000)},
                {name: '吉林',value: Math.round(Math.random()*1000)},
                {name: '福建',value: Math.round(Math.random()*1000)},
                {name: '贵州',value: Math.round(Math.random()*1000)},
                {name: '广东',value: Math.round(Math.random()*1000)},
                {name: '青海',value: Math.round(Math.random()*1000)},
                {name: '西藏',value: Math.round(Math.random()*1000)},
                {name: '四川',value: Math.round(Math.random()*1000)},
                {name: '宁夏',value: Math.round(Math.random()*1000)},
                {name: '海南',value: Math.round(Math.random()*1000)},
                {name: '台湾',value: Math.round(Math.random()*1000)},
                {name: '香港',value: Math.round(Math.random()*1000)},
                {name: '澳门',value: Math.round(Math.random()*1000)}
            ]
        }        
    ]
};
myChart.on('click', function (param){
    console.log(param.data.name);
    window.location.replace("province.html");
});
myChart.setOption(option);