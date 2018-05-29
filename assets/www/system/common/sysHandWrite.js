sys.namespace("sys.handwrite");
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
* 手写js
* 构造函数传入参数
*   param值类似
*       canvasId:canvas的id.字符串,必填，
*       lineWidth:手写线条的宽度.数字，可选，默认1.
*       strokeStyle:线条颜色。字符串，可选，默认"#019fe9"。
*       canvasXStart:x坐标偏移量.数字，可选，默认0.通过手指触摸或鼠标获得起点坐标是按照整个屏幕左上角开始的。
                     canvas画线是使用相对于自己的坐标。所以方法中中会自动减去响应便宜量。
*       canvasYStart:y坐标偏移量.数字，可选，默认0.
*       logfunc:打印日志的方法，可选，输入参数为日志内容.
* 对外提供方法：
*   getImgDataBase64 无参,获得手写图片内容
*   clearContent  无参，清除绘制内容
*
*
*/
sys.handwrite.class=function(param){
    sys.obj.mergeObjectOnLeft(param,
        {lineWidth:1,strokeStyle:"#019fe9",canvasXStart:0,canvasYStart:0}//默认值
      );//设置参数默认值
    this.canvasId=param.canvasId;
    this.canvas=document.getElementById(this.canvasId);
    this.canvas.handwrite=this;//挂到canvas节点对象上面
    this.board = this.canvas.getContext('2d');
    this.board.lineWidth = param.lineWidth;
    //board.strokeStyle="#0000ff";
    this.board.strokeStyle=param.strokeStyle;
    this.canvasXStart=param.canvasXStart;
    this.canvasYStart=param.canvasYStart;

    this.logfunc=null;
    if(param.logfunc) this.logfunc=param.logfunc;



    //获得base64编码的图片文件内容，字符串格式。可以直接设置到图片的src上显示。
    this.getImgDataBase64=function(){
          // dataUrl = dataUrl.replace("image/png","image/octet-stream");
          //$("#signpic").attr("src" , dataUrl);
          return this.canvas.toDataURL();
      };
    //清除手写内容
    this.clearContent=function(){
       // console.log(this.canvas.width+"  height:"+this.canvas.height);
        this.board.clearRect(0,0,this.canvas.width,this.canvas.height);
    };

    this.log=function(msg){
        //console.log(msg);

        if(this.logfunc){
            this.logfunc(msg);
        }

    };
    this.pos=function(event){
        var x,y;
        if(this.isTouch(event)){
            var bbox = this.canvas.getBoundingClientRect();
             //x = event.touches[0].pageX-this.canvasXStart
             //y = event.touches[0].pageY-this.canvasYStart
            y = (event.touches[0].pageY- bbox.top) * (this.canvas.height/bbox.height);
            x = (event.touches[0].pageX- bbox.left)* (this.canvas.width/bbox.width);
            this.log('posx='+x+' y='+y);
        }else{
            x = event.offsetX-this.canvasXStart;
            y = event.offsetY-this.canvasYStart;
        }
        this.log('x='+x+' y='+y);
        return {x:x,y:y};
    };

    this.isTouch=function(event){
        var type = event.type;
        if(type.indexOf('touch')>=0){
            return true;
        }else{
            return false;
        }
    };

    this.mousePress = false; //是否触屏中
    this.last = null;//画线上次坐标。
    this.beginDraw=function (event){
        this.log("beginDraw");
        this.mousePress = true;
    };

    this.drawing=function(event){

        event.preventDefault();
        if(!this.mousePress)return;
        var xy = this.pos(event);
        if(this.last!=null){

            this.board.beginPath();
            this.board.moveTo(this.last.x,this.last.y);
            this.board.lineTo(xy.x,xy.y);
            this.board.stroke();
        }
        this.last = xy;

    };

    this.endDraw=function(event){
        this.log("endDraw");
        this.mousePress = false;
        event.preventDefault();
        this.last = null;
    };


    this.canvas.onmousedown = sys.handwrite.beginDrawEvent;
    this.canvas.onmousemove = sys.handwrite.drawingEvent;
    this.canvas.onmouseup = sys.handwrite.endDrawEvent;
    this.canvas.addEventListener('touchstart',sys.handwrite.beginDrawEvent,false);
    this.canvas.addEventListener('touchmove',sys.handwrite.drawingEvent,false);
    this.canvas.addEventListener('touchend',sys.handwrite.endDrawEvent,false);

};
/**
* 获得事件中的手写对象。
* 说明：在创建对象的时候，会在canvas的节点上增加handwrite属性，将创建的对象挂在该属性上。
*/
sys.handwrite.getHandWriteObj=function(event){
  var list=[event.target,event.fromElement,event.srcElement,event.toElement];
  for(var i=0;i<list.length;i++){
    var curObj=list[i];
    if(curObj && curObj.handwrite) return curObj.handwrite;
  }
  return null;
};
/**
* 手写开始事件
*/
sys.handwrite.beginDrawEvent=function(event){
    var handwrite=sys.handwrite.getHandWriteObj(event);
    if(handwrite){
        handwrite.beginDraw(event);
    }
};
/**
* 手写进行事件
*/
sys.handwrite.drawingEvent=function(event){
    var handwrite=sys.handwrite.getHandWriteObj(event);
    if(handwrite){
        handwrite.drawing(event);
    }
};
/**
* 手写完成事件
*/
sys.handwrite.endDrawEvent=function(event){
    var handwrite=sys.handwrite.getHandWriteObj(event);
    if(handwrite){
        handwrite.endDraw(event);
    }
};