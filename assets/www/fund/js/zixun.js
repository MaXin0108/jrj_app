
function tbNewList(){
            var html = '';
            sys.plugin.HTTP.post({
                tips:"loading...",
                data:[{system:"fund",action:"http://39.105.130.74:8080/jrj/findAllTbNews",data:{}}],
                dataType:"json",
                successFunc:function(MSG){
                    var data = MSG.tbNewList;
                    for(var i=0;i<data.length;i++){
                        var guid = data[i].guid;
                        var title = data[i].newsTitle;
                        var website = data[i].newsWebsite;
                        var newLink = data[i].newsSourceLink;
                        var newsTime = data[i].newsSourceDistributeTime;
                        var clickNumber = data[i].newsClick;
                        html+='<div class="container_fluid getNewLink">'
                            +'  <input class="visit" hidden type="text" value="'+guid+'"><input class="visit_link" hidden type="text" value="'+newLink+'">'
                            +'     <div class="topline_menu" style="padding:10px 10px">'
                            +'         <div style="float:left;font-size:16px;width: 60%">'
                            +'             <a herf="'+ newLink +'"><p>'+title+'</p></a>'
                            +'         </div>'
                            +'     <div style="float:right;width:33%">'
                            +'         <img src="images/jrjlogo.png" style="height:100px;float:right"/>'
                            +'     </div>'
                            +'     <div style="width:100%;float:left">'
                            +'         <span style="font-size:14px">'+website+' '+'</span>'
                            +'         <span style="font-size:14px;float:right;margin-right:24px;">浏览量 '+clickNumber+'</span>'
                            +'     </div>'
                            +' </div>'
                            +'</div>'
                            + '<br><div style="width:100%;height:1px;margin:0px auto;padding:0px;background-color:#D5D5D5;overflow:hidden;"></div>'


                    }
                    $("#news").html(html);
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

        tbNewList();

    }
};
app.initialize();