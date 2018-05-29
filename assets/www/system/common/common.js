sys={};
/**
 * 功能：建立对象，与Ext.namespace功能相同
 * 参数：字符串，可以是多个，以后逗号分隔
 * 示例：sys.namespace("sys.map.baidu.geocoder");sys.namespace("sys.page","sys.ajax");
 */
sys.namespace = function() {
	var a = arguments, o = null, i, j, d, rt;
	for (i = 0; i < a.length; ++i) {
		d = a[i].split(".");
		rt = d[0];
		eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = '
				+ rt + ';');
		for (j = 1; j < d.length; ++j) {
			o[d[j]] = o[d[j]] || {};
			o = o[d[j]];
		}
	}
};

sys.namespace("sys.json");
sys.namespace("sys.cordova");
/**
 * 功能：封装cordova.exec
 * 参数：
 *      param 参数数据，其中属性
 *           successFunc 成功回调函数。函数格式为function(data),data为返回的业务数据
 *           errorFunc 失败回调函数。函数格式为function(err)，err格式为{success:false,msg:"",data:数据对象}。其中msg为错误信息
 *           data 为要提交的数据，格式为数组。
 *           tips 提示内容，如果传入，就提示
 * 示例：
 */
sys.cordova.exec=function(param, service, action){
//alert(JSON.stringify(param.data[0].data.username))
    if(param.tips){
        sys.plugin.Tips.showModalDialogWithCallBack(param.tips
            ,function(showDialogStr){
                cordova.exec(function(winParamStr){
                                    console.log("success return value: \n" + winParamStr);
                                    sys.plugin.Tips.hiddenModalDialogWithCallBack(function(hiddenDialogStr){
                                        var winParam=sys.json.convertStringToObj(winParamStr);
                                        if(winParam.success){
                                            if(param.successFunc)param.successFunc(winParam.data);
                                        }else{
                                            if(param.errorFunc)param.errorFunc(winParam);
                                        }
                                    });
                              }, function(errStr) {
                                console.log("error return value: \n" + errStr);
                                sys.plugin.Tips.hiddenModalDialogWithCallBack(function(hiddenDialogStr){
                                    var err=sys.json.convertStringToObj(errStr);
                                    if(param.errorFunc)param.errorFunc(err);
                                });
                              },service, action,param.data);
                              //},service, action,[{system:"fund",action:"jrzcapp/agentTbUser/login",data:{'123':"123"}}]);
        });
    }else{ //没有增加等待信息
        cordova.exec(function(winParamStr){
                    console.log("success return value: \n" + winParamStr);
                    var winParam=sys.json.convertStringToObj(winParamStr);
                    if(winParam.success){
                        if(param.successFunc)param.successFunc(winParam.data);
                    }else{
                        if(param.errorFunc)param.errorFunc(winParam);
                    }
                }, function(errStr) {
                    console.log("error return value: \n" + errStr);
                    var err=sys.json.convertStringToObj(errStr);
                    if(param.errorFunc)param.errorFunc(err);
                }, service, action, param.data);
    }
};

/**
 * 功能：为对象创建cordova方法
 * 参数：
 *      param 参数数据，所有插件的param格式同该参数说明，在插件中只是描述data属性的要求。其中属性
 *           successFunc 成功回调函数。函数格式为function(data),data为返回的业务数据
 *           errorFunc 失败回调函数。函数格式为function(err)，err格式为{success:false,msg:"",data:数据对象}。其中msg为错误信息
 *           data 为要提交的数据，格式为数组。
 * 示例：
 */
sys.cordova.execCreateFunction=function(obj,service,actionArray){
    for(var i=0;i<actionArray.length;i++){
        obj[actionArray[i]]=new Function('param','sys.cordova.exec(param,"'+service+'","'+actionArray[i]+'");');
    }
};
/**
 * 功能：将字符串转换为对象
 * 参数：
 * 示例：
 */
sys.json.convertStringToObj=function(str){
    return eval("("+str+")");
};
/**
 * 功能：全局上下文MUOContext对象,在所有页面都存在。可以用于数据传递，处理
 * putString,向MUOContext中设置数据
 *      参数:param,其中属性data格式为{key:value}，允许传入多个
 *      返回:成功时,data值为""
 *      示例：sys.plugin.MUOContext.putString(null,null,{"p1":"1","p2","2"});
 * getString,从MUOContext中获得数据
 *      参数:param,其中属性data为数组，可以获得多个值
 *      返回:成功时,data值为""
 *      示例：sys.plugin.MUOContext.getString(null,null,["p1","p2"]);
 */
sys.namespace("sys.plugin.MUOContext");
sys.cordova.execCreateFunction(sys.plugin.MUOContext,"MUOContextPlugin"
    ,["putString","getString","removeString","getAllKey","getAllData"]);

/**
 * 功能：数据库操作对象
 *
 */
sys.namespace("sys.plugin.DB");
sys.cordova.execCreateFunction(sys.plugin.DB,"DBPlugin"
    ,["doInsert","doExecSql","doUpdate","doSave","doDelete"
        ,"doQuery","doQueryOne","doFindOne","doFind"]);
/**
 * 功能：同步对象
 *
 */
sys.namespace("sys.plugin.Syn");
sys.cordova.execCreateFunction(sys.plugin.Syn,"SynPlugin"
    ,["synAndQueryData"]);
/**
 * 功能：http对象
 *
 */
sys.namespace("sys.plugin.HTTP");
sys.cordova.execCreateFunction(sys.plugin.HTTP,"HttpPlugin"
    ,["post"]);

/**
 * 功能：活动对象
 *
 */
sys.namespace("sys.plugin.Activity");
sys.cordova.execCreateFunction(sys.plugin.Activity,"ActivityPlugin"
    ,["switchToLogin"]);
/**
 * 功能：显示提示
 *
 */
sys.namespace("sys.plugin.Tips");
sys.plugin.Tips.showTips=function(tips){
    cordova.exec(null, null, "TipsPlugin", "showTips", [tips]);
};
sys.plugin.Tips.showModalDialog=function(tips){
    cordova.exec(null, null, "TipsPlugin", "showModalDialog", [tips]);
};
sys.plugin.Tips.showModalDialogWithCallBack=function(tips,callbackFunc){
    cordova.exec(callbackFunc, callbackFunc, "TipsPlugin", "showModalDialog", [tips]);
};
sys.plugin.Tips.hiddenModalDialog=function(){
    cordova.exec(null, null, "TipsPlugin", "hiddenModalDialog", []);
};
sys.plugin.Tips.hiddenModalDialogWithCallBack=function(callbackFunc){
    cordova.exec(callbackFunc, callbackFunc, "TipsPlugin", "hiddenModalDialog", []);
};
/**
 * 功能：打印功能
 *
 */
sys.namespace("sys.plugin.Gprinter");
sys.cordova.execCreateFunction(sys.plugin.Gprinter,"GprinterPlugin"
    ,["getConnectInfo","getMatchedBlueToothInfo","scanBlueTooth","connect","print"]);

/**
 * 功能：版本功能。获得当前版本，版本升级
 *
 */
sys.namespace("sys.plugin.Version");
sys.cordova.execCreateFunction(sys.plugin.Version,"VersionPlugin"
    ,["upgrade","getVersion","getCurAndLatestVersion"]);
/*
这里版本低
plugman install --platform android --project android  --plugin org.apache.cordova.battery-status --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.camera --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.console --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.contacts --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.device --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.device-motion  --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.device-orientation --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.dialogs --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.file --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.file-transfer --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.geolocation --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.globalization --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.inappbrowser --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.media --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.media-capture --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.network-information --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.splashscreen --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd
plugman install --platform android --project android  --plugin org.apache.cordova.vibration --plugins_dir android/assets/www/frame3rd/plugins --www android/assets/www/frame3rd

用下面的版本安装
plugman install --platform ios --project ios  --plugin cordova-plugin-battery-status
plugman install --platform ios --project ios  --plugin cordova-plugin-camera

plugman install --platform ios --project ios  --plugin cordova-plugin-contacts
plugman install --platform ios --project ios  --plugin cordova-plugin-device
plugman install --platform ios --project ios  --plugin cordova-plugin-device-motion
plugman install --platform ios --project ios  --plugin cordova-plugin-device-orientation
plugman install --platform ios --project ios  --plugin cordova-plugin-dialogs
plugman install --platform ios --project ios  --plugin cordova-plugin-file
plugman install --platform ios --project ios  --plugin cordova-plugin-file-transfer
plugman install --platform ios --project ios  --plugin cordova-plugin-geolocation
plugman install --platform ios --project ios  --plugin cordova-plugin-globalization
plugman install --platform ios --project ios  --plugin cordova-plugin-inappbrowser

plugman install --platform ios --project ios  --plugin cordova-plugin-media-capture
plugman install --platform ios --project ios  --plugin cordova-plugin-network-information
plugman install --platform ios --project ios  --plugin cordova-plugin-splashscreen
plugman install --platform ios --project ios  --plugin cordova-plugin-vibration
plugman install --platform ios --project ios  --plugin cordova-plugin-statusbar

cordova plugin add cordova-plugin-battery-status
cordova plugin add cordova-plugin-camera

cordova plugin add cordova-plugin-contacts
cordova plugin add cordova-plugin-device
cordova plugin add cordova-plugin-device-motion
cordova plugin add cordova-plugin-device-orientation
cordova plugin add cordova-plugin-dialogs
cordova plugin add cordova-plugin-file
cordova plugin add cordova-plugin-file-transfer
cordova plugin add cordova-plugin-geolocation
cordova plugin add cordova-plugin-globalization

cordova plugin add cordova-plugin-inappbrowser
cordova plugin add cordova-plugin-media-capture
cordova plugin add cordova-plugin-network-information
cordova plugin add cordova-plugin-splashscreen
cordova plugin add cordova-plugin-vibration
cordova plugin add cordova-plugin-statusbar
cordova plugin add phonegap-plugin-barcodescanner
*/

sys.namespace("sys.obj");
/**
 * 功能：对象，初始化参数,目前只针对一级。
 * 使用场景：对外提供公用方法的时候，有些参数提供了默认值；这样就不需要外部再进行传入。
 *        为了让外部很容易到底有哪些默认值，在输入参数对象上面将默认值也加上。
 * 参数：param  参数对象
 *      defaultObject 默认值对象
 * 示例：var param={a:1};sys.obj.initParam(param,{a:2,b:1});执行后，param的值变为{a:1,b:1}
 */
sys.obj.mergeObjectOnLeft=function(param,defaultObject){
    for(var p in defaultObject){
        if(!param.hasOwnProperty(p)){
            param[p]=defaultObject[p];
        }
    }
    return param;
};
/**
 * 功能：以alert提示对象属性
 * 参数：obj  参数对象
 * 示例：无
 */
sys.obj.showProperties=function(obj){
    var str="";
    if(obj==null) alert("对象为null");
    for(var p in obj){
        str=str+p+"="+obj[p]+"\n";
    }
    alert(str);
};

/*获取链接请求参数*/
sys.getQueryString = function(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
        return unescape(r[2]);
	}
	return "";
};
/*获取连接请求的对象*/
sys.getQueryObject = function(obj){
    return obj;
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
//author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/*弹出提示框*/
sys.alert = function(content) {
    $("#body_bg").remove();
    var alert = ''
            + '<div id="body_bg" style="display:none;width:100%; height:100%; position:fixed; top:0px; left:0; z-index:999999;background:rgba(0,0,0,0.35);">'
            + '<div id="alert" style="position: relative;margin:0 auto;width:70%;min-height: 50px;margin-top:38%;padding-bottom: 30px;  background-color: #FFFFFF; -webkit-border-radius: 10px;-moz-border-radius: 10px;border-radius: 10px;">'
            + '<div id="alert_content" style="padding:17px 10px; min-height:34px;line-height:22px;font-size:14px; text-align: center;"></div>'
            + '<div id="alert_btn" style="position: absolute;left: 0;bottom: 0; width: 100%;height: 40px;line-height: 40px;font-size:16px; text-align: center; border-top: 1px solid #ccc;">OK</div>'
            + '</div>'
            + '</div>';
    $("body").append(alert);

    $("#body_bg").css("display", "block");
    $("#alert_content").html(content);
    $("#alert_btn").unbind("click");
    $("#alert_btn").click(function() {
        $("#body_bg").css("display", "none");
        //fn();
    });
};

sys.guid = function() {
    function S4() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }
    return (S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4());
};

//跳转页面
function gotoPage(page) {
        var project_id = sys.getQueryString('project_id');
        var project_name = sys.getQueryString('project_name');
        var accountId = sys.getQueryString('accountId');
        var investment_name = sys.getQueryString('investment_name');
        var get = "?project_name="+project_name+"&accountId=" + accountId +""+"&project_id=" + project_id+"&investment_name=" + investment_name;
        if(page==5){
            location.href = "mobile-5.html"+get;
        }else if(page==4){
             location.href = "mobile-4.html"+get;
        }else if(page==3){
             location.href = "mobile-3.html"+get;
        }else if(page==2){
             location.href = "mobile-2.html"+get;
        }else if(page==1){
             location.href = "mobile-1.html"+get;
        }
}

