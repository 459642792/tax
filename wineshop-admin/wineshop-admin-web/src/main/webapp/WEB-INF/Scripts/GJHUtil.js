var gjhUtil = function () {
    var sf = this;

    sf.sharePage = function () {
        if ($(".gjh-share").is(":hidden")) {
            var isWX = false;
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                //微信
                isWX = true;
            }
            /*分享*/
            if (isWX) {
                $("body").append('<div class="mask" style="z-index:9999;display:block;" ></div>');
                $(".gjh-share").show();
                $(".gjh-share img").addClass("scaleImg");
            } else {
                window.open("http://www.jiathis.com/send/?webid=tsina&url=http://www.ganjiuhui.com&pic=http://www.ganjiuhui.com/Content/images/Public/logo.png&title=赶酒会，国内最专业的酒水代理平台 " + window.location.href);
            }
        }
    }

    //记录data source
    sf.getQueryString = function (name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return "";
    }

    sf.goBack = function (fromUrl, sharebackUrl) {
        if (fromUrl && $.trim(fromUrl) != '') {
            window.location.href = decodeURIComponent(fromUrl);
        }
        else if (document.referrer != "") {
            if (navigator.userAgent.indexOf("360 Aphone Browser") != -1) {
                window.location = document.referrer;
            }
            else {
                window.history.back(-1);
            }
        }
        else {
            if (sharebackUrl && $.trim(sharebackUrl) != '') {
                window.location.href = sharebackUrl;
            }
            else {
                window.location.href = "/";
            }
        }
    }

    sf.addVisitTrack = function (foreignId, visitType) {
        $.getJSON("/Common/AddVisitTrack", {
            foreignId: foreignId,
            visitType: visitType,
            referrer: document.referrer,
            currentUrl: document.location.href
        }, function (data, status) {
        });
    }
}

var gjhModel = new gjhUtil();

$(".mask, .gjh-share").live('click', function () {
    $(".gjh-share").hide();
    $(".mask").remove();
});

window.commonRegex = {
    phone: function () {
        return /^1[34578][0-9]{9}$|^0[0-9]{2,3}[-_/\\.]?[0-9]{7,8}$/;
    },
    email: function () {
        return /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    }
}

window.gjhAlert = function (content, title, yesFunction) {
    $.confirm({
        title: title, //标题，不传值或者为空，modal将不会显示confirm-head
        msg: content,//提示的内容
        confirmButtonClass: "btn-minor",//确认按钮的class
        cancelButtonClass: "dnone",//取消按钮的class
        confirmButton: "确认",//确定按钮的文字
        cancelButton: "取消",//取消按钮的文字
        confirm: function () {//点击确定之后要执行的方法，不传值不执行任何操作，confirm-modal将关闭
            if (typeof (yesFunction) == 'function') {
                yesFunction();
            }
        },
        cancel: function () {//点击取消之后要执行的方法，不传值不执行任何操作，confirm-modal将关闭
            alert("取消");
        },
    });
}

//将list转为table显示的表格, 建议使用方法castTableV2
window.castTable = function (list, columnCount) {
    var result = [];
    if (list instanceof Array) {
        for (var i = 0; i < list.length; i = i + 2) {
            var temp = [];
            temp.push(list[i]);

            if (i + 1 < list.length) {
                temp.push(list[i + 1]);
            }

            result.push(temp);
        }
    }

    return result;
}

//将list转为table显示的表格
window.castTableV2 = function (list, columnCount) {
    var result = [];
    if (list instanceof Array) {
        for (var i = 0; i < list.length; i = i + columnCount) {
            var temp = [];

            for (var j = i; j < i + columnCount && j < list.length; j++) {
                temp.push(list[j]);
            }

            result.push(temp);
        }
    }

    return result;
}

//
function getScrollTop() {
    var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
    if (document.body) {
        bodyScrollTop = document.body.scrollTop;
    }
    if (document.documentElement) {
        documentScrollTop = document.documentElement.scrollTop;
    }
    scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
    return scrollTop;
}

//文档的总高度
function getScrollHeight() {
    var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
    if (document.body) {
        bodyScrollHeight = document.body.scrollHeight;
    }
    if (document.documentElement) {
        documentScrollHeight = document.documentElement.scrollHeight;
    }
    scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
    return scrollHeight;
}

//浏览器视口的高度
function getWindowHeight() {
    var windowHeight = 0;
    if (document.compatMode == "CSS1Compat") {
        windowHeight = document.documentElement.clientHeight;
    } else {
        windowHeight = document.body.clientHeight;
    }
    return windowHeight;
}