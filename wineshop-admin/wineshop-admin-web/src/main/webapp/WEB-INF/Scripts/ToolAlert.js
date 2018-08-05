/*
*在需要显示提示的元素上方显示一个提示框
*
*使用本函数必须调用以下文件
*   ToolAlert.css
*   jQuery.js
*   ToolAlert.js
*
*CSS中内容根据文件夹目录放置自行修改
*
*函数介绍:
*$(选择器).ToolAlert(Msg,BgColor,Speed);
*参数说明:
*   Msg(String)[必须]:需要在提示框上显示的内容
*   BgColor(String)[可选]:显示提示框颜色代码 如#FFFFFF等
*   Speed(String,Number)[可选]:三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000) 默认200
*
*例:在id="idx"的元素上方弹出一个黄色背景提示框
*$("#idx").ToolAlert("提示框...",'#FF0',150);
*/
(function ($) {
    $.fn.ToolAlert = function (Msg, BgColor, Speed) {
        var reg = /^[0-9]+$|^fast$|^normal$|^slow$/i;
        if (!reg.test(Speed)) {
            Speed = 200;
        }
        var ToolObj = this;
        var AfterEle = ToolObj.next();
        if (AfterEle.is(".ToolDiv")) {
            if (window.forcibly == true) {
                AfterEle.stop(true, true).remove();
            }
            else {
                if ($.trim(AfterEle.text()) != $.trim(Msg)) {
                    AfterEle.stop(true, true).remove();
                }
                else {
                    return;
                }
            }
        }
        var newTool = $('<div class="ToolDiv tooltip fade top in" style="display: block;z-index:1000;padding-bottom:7px;"><div class="tooltip-arrow" style="left:15px;margin-left:0px;border-width:7px 7px 0px 7px;"></div><div class="tooltip-inner" style="min-width:80px;"></div></div>');
        var elText = newTool.children(".tooltip-inner");
        var elHorn = newTool.children(".tooltip-arrow");
        elText[0].innerText = Msg;
        newTool.css("display", "none");
        if ($.browser.msie && parseInt($.browser.version) <= 6) {
            newTool.css("height", 14);
        }
        ToolObj.after(newTool);
        if (BgColor != null) {
            newTool.css("background", BgColor).children("div").children("div:lt(8)").css("background", BgColor);
        }
        if ($.browser.msie && parseInt($.browser.version) <= 7) {
            newTool.css("width", 150);
        }
        var DRemote = newTool.outerHeight(true) + elHorn.height() + 21;
        var DLeft = 0;
        if (ToolObj.outerWidth(false) < 30) {
            DLeft = 15;
        }
        if ((ToolObj.css("position") || "").toLowerCase() == "fixed" && parseInt($.browser.version) >= 7) {
            newTool.css({
                position: "fixed",
                left: (parseInt(ToolObj.css("left")) - DLeft),
                top: (parseInt(ToolObj.css("top")) - DRemote)
            });
            //关于Top/Left为百分比时 可能会有问题
        }
        else {
            var os = ToolObj.FixedoffsetParent();
            newTool.css({position: "absolute", left: (os.left - DLeft), top: (os.top - DRemote)});
        }
        newTool.css("display", "block").animate({top: (parseInt(newTool.css("top")) + 20)}, Speed);
        newTool.one('click', function () {
            $(this).remove();
        });
    };

    $.fn.RemoveToolAlert = function () {
        var ToolObj = this;
        var TA = ToolObj.next();
        if (TA.is(".ToolDiv")) {
            TA.remove();
        }
    };

    $.fn.ClearToolAlert = function () {
        var ToolObj = this;
        ToolObj.find(".ToolDiv").remove();
    };

    ClearAllToolAlert = function () {
        $(".ToolDiv").remove();
    };

    $.fn.FixedoffsetParent = function () {
        var tThis = this.eq(0);
        var WorkThis = tThis.parents("*");
        var osThis = tThis.offset();
        var jsData = {left: 0, top: 0};
        for (var i = 0; i < WorkThis.length; i++) {
            if (WorkThis.eq(i).css("position").toLowerCase() == "fixed" || WorkThis.eq(i).css("position").toLowerCase() == "absolute" || WorkThis.eq(i).css("position").toLowerCase() == "relative" || WorkThis.eq(i).is("body")) {
                if (!WorkThis.eq(i).is("body")) {
                    var osParent = WorkThis.eq(i).offset();
                    jsData.left = osThis.left - osParent.left;
                    jsData.top = osThis.top - osParent.top;
                }
                else {
                    jsData.left = osThis.left;
                    jsData.top = osThis.top;
                }
                break;
            }
        }
        return jsData;
    }
})(jQuery);