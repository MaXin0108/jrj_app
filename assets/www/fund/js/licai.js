
function investmentList(){
            var html = '';
            sys.plugin.HTTP.post({
                tips:"loading...",
                data:[{system:"fund",action:"http://39.105.130.74:8080/jrj/findAllTbInvestment",data:{}}],
                dataType:"json",
                successFunc:function(MSG){
                    var msg = MSG.investmentList;
                    for(var i=0;i<msg.length;i++){
                    html += '<table><tr><th colspan="3">'
                            + msg[i].brandName
                            + '</th></tr><tr><td colspan="3" style="border-bottom:#ddd 1px solid;"><div class="pro-text">体验投资,快速回报</div><div class="pro-yuan">起投金额：'
                            + msg[i].startInvestAmount
                            + '</div><div class="clearfix"></div></td></tr><tr class="pro-dta"><td><span>平台年化</span><strong class="orange">'
                            + msg[i].incomereferencevalue
                            + '%</strong><span>平台红包</span><strong class="orange">'
                            + msg[i].redenvelopes
                            + '%</strong></td><td><span>期限:</span<strong>'
                            + msg[i].updaycount
                            + '天</strong></td><td><a href="'
                            + msg[i].statelink
                            + '" class="ljyy">立即预约</a></td></tr></table>'
                    }
                    $("#product").html(html);
                },errorFunc:function(err){
               // sys.alert(err.investmentList[0].brandName + "false!");
                    sys.alert("网络连接无效");
                //
                //location.href = "http://www.baidu.com";
                   }
            });
    }


var app = {
    initialize: function () {
        this.bindEvents();
    },
    bindEvents: function () {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    onDeviceReady: function () {
        app.receivedEvent('deviceready');
    },
    receivedEvent: function (id) {
        var type = sys.getQueryString("type");
        var table_val = !sys.getQueryString("table_val")?"type1":sys.getQueryString("table_val");
        $("#table_val").val(table_val);
        $("#returnTop").click(function () {
            var speed=200;//滑动的速度
            $('body,html').animate({ scrollTop: 0 }, speed);
            return false;
        });
       //日期初始化
        var curr = new Date().getFullYear();
        var opt={};
        opt.date = {preset : 'date'};
        opt.datetime = {preset : 'datetime'};
        opt.time = {preset : 'time'};
        opt.default = {
            theme: 'android-holo light', //皮肤样式
            display: 'bottom', //显示方式
            mode: 'scroller', //日期选择模式
            dateFormat: 'yyyy/mm/dd',
            lang: 'zh',
            showNow: true,
            nowText: "今天",
            stepMinute: 5,
            startYear: curr - 0, //开始年份
            endYear: curr + 0 //结束年份
        };

        investmentList();

    }
};
app.initialize();