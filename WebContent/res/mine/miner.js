var step = 200;
$(function(){
	//tab向左
	$(".tab-content .tab-left").click(function(){
		var left = $(".tab-content .tab-list .lis").css("margin-left");
		var now = parseInt(left.replace("px", "")) + step;
		if(now > 0){
			now = 0;
		}
		$(".tab-content .tab-list .lis").animate({'margin-left': now+'px'});
	})
	//tab向右
	$(".tab-content .tab-right").click(function(){
		var containerWidth = $(".tab-content .tab-list").width();
		var countWith = 0;
		$(".tab-content .tab-list .lis li").each(function(){
			countWith += ($(this).width() + 31);
		})
		if((containerWidth - countWith) > 0){
			return;
		}
		var left = $(".tab-content .tab-list .lis").css("margin-left");
		var now = parseInt(left.replace("px", "")) - step;
		if((countWith - containerWidth + step) < Math.abs(now)){
			return;
			//now = (containerWidth - countWith);
		}
		$(".tab-content .tab-list .lis").animate({'margin-left': now+'px'});
	})
	//tab激活
	$("body").delegate(".tab-content .tab-list .lis li", "click", function(){
		$(".tab-content .tab-list .lis li").removeClass("active");
		$(this).addClass("active");
		//保证被激活tab展示到list上
		var containerWidth = $(".tab-content .tab-list").width();
		var left = parseInt($(".tab-content .tab-list .lis").css("margin-left").replace("px", ""));
		var countWith = 0;
		var nowEl = $(this);
		$(".tab-content .tab-list .lis li").each(function(){
			if(nowEl.data("id") == $(this).data("id"))return false;
			countWith += ($(this).width() + 31);
		})
		if(containerWidth > countWith){
			$(".tab-content .tab-list .lis").animate({'margin-left': 0+'px'});
		}else if(Math.abs(left) > countWith){
			$(".tab-content .tab-list .lis").animate({'margin-left': (-countWith)+'px'});
		}else if((Math.abs(left) + countWith + nowEl.width() + 31) > containerWidth){
			$(".tab-content .tab-list .lis").animate({'margin-left': (containerWidth - countWith - nowEl.width() - 31)+'px'});
		}
		
		//显示相应的iframe
		var id = nowEl.data("id");
		$(".tab-iframes iframe").hide();
		$(".tab-iframes iframe[data-id='"+id+"']").show();
		
	})
	//tab关闭
	$("body").delegate(".tab-content .tab-list .lis li .tab-close", "click", function(){
		
		var id = $(this).parent().data("id");
		$(".tab-iframes iframe[data-id='"+id+"']").remove();
		var next = $(this).parent().next();
		var prev = $(this).parent().prev();
		$(this).parent().remove();
		if(next.length){
			next.click();
		}else{
			prev.click();
		}
		
		return false;
		
		//TODO
	})
})

function addTab(id, title, src){
	var hasTab = $(".tab-content .tab-list .lis li[data-id='"+id+"']");
	if(hasTab.length){
		var exitIframe = $(".tab-iframes iframe[data-id='"+id+"']");
		exitIframe.attr("src", src);
		hasTab.click();
	}else{
		newTab(id, title, src);
		var containerWidth = $(".tab-content .tab-list").width();
		var countWith = 0;
		$(".tab-content .tab-list .lis li").each(function(){
			countWith += ($(this).width() + 31);
		})
		if(countWith > containerWidth){
			$(".tab-content .tab-list .lis").animate({'margin-left': (containerWidth - countWith)+'px'});
		}
	}
}
function newTab(id, title, src){
	$(".tab-content .tab-list .lis li").removeClass("active");
	var exitTab = $(".tab-content .tab-list .lis li[data-id='"+id+"']");
	if(exitTab.length > 0){
		exitTab.click();
	}else{
		$(".tab-content .tab-list .lis").append('<li class="active" data-id="'+id+'"><span>'+title+'</span><span class="tab-close"><i class="fa fa-close"></i></span></li>');
	}
	var exitIframe = $(".tab-iframes iframe[data-id='"+id+"']");
	$(".tab-iframes iframe").hide();
	if(exitIframe.length > 0){
		exitIframe.show();
	}else{
		$(".tab-iframes").append('<iframe data-id="'+id+'" class="tab-item" src="'+src+'"></iframe>');
	}
}
















