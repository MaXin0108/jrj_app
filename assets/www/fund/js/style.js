$(function(){
	//最后一个没有边框
	$("nav dl:last,.shouyi dl:last").css("border","0");
	//.bankList li 银行选择	
	$(".bankList li").click(function(){
		$(this).addClass("banSty").siblings("li").removeClass("banSty");
		})	
	//添加银行卡	
	$(".tianjiayinhang").click(function(){
		$(".addYinhang").fadeIn();
		})	
	$(".glyphicon-remove").click(function(){
		$(".addYinhang").fadeOut();
		})		
	})
	
	
function next()
{
document.write("<a href='javascript:history.go(-1)'>后退</a>   &nbsp;&nbsp;<a href='javascript:history.go(0)'>刷新</a>&nbsp;&nbsp;<a href='javascript:history.go(1)'>前进</a>   <form>   <input name='ht' type='button' onclick='javascript:history.go(-1)' value='后退' />   <input name='sx' type='button' onclick='javascript:history.go(0)' value='刷新' />   <input name='qj' type='button' onclick='javascript:history.go(1)' value='前进' />   </form> ");
}	